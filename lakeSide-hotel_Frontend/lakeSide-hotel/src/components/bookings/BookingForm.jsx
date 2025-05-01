import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import {bookRoom, getRoomById} from '../utils/ApiFunctions';
import moment from 'moment';
import { Form, FormControl } from 'react-bootstrap';
import BookingSummary from '../bookings/BookingSummary'



const BookingForm = () => {
    const[isValidated, setIsValidated]=useState(false);
    const[isSubmitted, setIsSubmitted]=useState(false)
    const[errorMessage, setErrorMessage]=useState("")
    const[roomPrice, setRoomPrice]=useState(0);
    const[booking,setBooking]=useState({
        guestName: "",
        guestEmail: "",
        checkInDate: "",
        checkOutDate: "",
        numberOfAdults: "",
        numberOfChildren: ""
    })

    const[roomInfo, setRoomInfo]=useState({
      photo:"",
      roomType:"",
      roomPrice:""
  })

  const {roomId}=useParams()
  const navigate= useNavigate()

  const getRoomPriceById=async (roomId)=>{
    try {
      const response=await getRoomById(roomId)
      setRoomPrice(response.roomPrice)
    } catch (error) {
      throw new Error(error)
      
    }
  }

    const handleInputChange=(e)=>{
        const{name, value}= e.target
        setBooking({...booking, [name]: value})
        setErrorMessage("")
    }

    useEffect(()=>{
      getRoomPriceById(roomId)
    },[roomId])

    const calculatePayment=()=>{
      const checkInDate=moment(booking.checkInDate)
      const checkOutDate= moment(booking.checkOutDate)
      const diffInDays= checkOutDate.diff(checkInDate)
      const price= roomPrice ? roomPrice : 0
      return diffInDays * price
    }

    const isGuestCountValid=()=>{
      const adultCount= parseInt(booking.numberOfAdults)
      const childrenCount= parseInt(booking.numberOfChildren)
      const totalCount= adultCount + childrenCount;
      return totalCount >= 1 && adultCount >= 1; 
    }

    const isCheckOutDateValid=()=>{
      if(!moment(booking.checkOutDate).isSameOrAfter(moment(booking.checkInDate))){
        setErrorMessage("Check-out date must come before check-in date")
        return false
      }else{
        setErrorMessage("")
        return true
      }
    }

const handleSubmit=(e)=>{
  e.preventDefault();
  const form=e.currentTarget
  if(form.checkValidity()=== false || !isGuestCountValid || !isCheckOutDateValid()){
    e.stopPropagation()
  }else{
    setIsSubmitted(true)
  }
  setIsValidated(true)
}

const handleBooking= async ()=>{
  try {
    const confirmationCode= await bookRoom(roomId, booking)
    setIsSubmitted(true)
    navigate("/",{state: {message : confirmationCode}})
  } catch (error) {
    setErrorMessage(error.message)
    navigate("/",{state: {error : errorMessage}})

  }
};

  return (
    <>
    <div className='container md-5'>
      <div className='row'>
        <div className='col-md-6'>
          <div className='card card-body mt-5'>
            <h4 className='card card-title'>Reserve Room</h4>
            <Form noValidate validated={isValidated} onSubmit={handleSubmit}>
              <Form.Group>
                <Form.Label htmlFor='guestName'>
                  Full Name:
                </Form.Label>
              
              <FormControl
              required
              type='text'
              id='guestName'
              name='guestName'
              value={booking.guestName}
              placeholder='Enter your full name'
              onChange={handleInputChange}/>
              <Form.Control.Feedback type='invalid'>
                Please enter your fullname
              </Form.Control.Feedback>
              </Form.Group>



              <Form.Group>
                <Form.Label htmlFor='guestEmail'>
                  Email:
                </Form.Label>
              
              <FormControl
              required
              type='email'
              id='guestEmail'
              name='guestEmail'
              value={booking.guestEmail}
              placeholder='Enter your email'
              onChange={handleInputChange}/>

              <Form.Control.Feedback type='invalid'>
                Please enter your email
              </Form.Control.Feedback>
              </Form.Group>




              <fieldset style={{border: "2px"}}>
                <legend>Lodging period</legend>
                <div className='row'>
                  <div className='col-6'>  
              <Form.Label htmlFor='checkInDate'>Check-In date</Form.Label>
              <FormControl
              required
              type='date'
              id='checkInDate'
              name='checkInDate'
              value={booking.checkInDate}
              placeholder='Check-in date'
              onChange={handleInputChange}/>
              <Form.Control.Feedback type='invalid'>
                Please select a check-in-date
              </Form.Control.Feedback>              
                  </div>


                  <div className='col-6'>  
              <Form.Label htmlFor='checkOutDate'>Check-Out date</Form.Label>
              <FormControl
              required
              type='date'
              id='checkOutDate'
              name='checkOutDate'
              value={booking.checkOutDate}
              placeholder='Check-out date'
              onChange={handleInputChange}/>
              <Form.Control.Feedback type='invalid'>
                Please select a check-out-date
              </Form.Control.Feedback>              
                  </div>
                  {errorMessage && <p className='error-message text-danger'>{errorMessage}</p>}
                </div>

              </fieldset>

              <fieldset>
                <legend>Number of guest</legend>
                <div className='row'>
                  <div className='col-6'>  
              <Form.Label htmlFor='numberOfAdults'>Adults</Form.Label>
              <FormControl
              required
              type='number'
              id='numberOfAdults'
              name='numberOfAdults'
              value={booking.numberOfAdults}
              min={1}
              placeholder='0'
              onChange={handleInputChange}/>
              <Form.Control.Feedback type='invalid'>
                Please select at least 1 adult.
              </Form.Control.Feedback>              
                  </div>

                  <div className='col-6'>  
              <Form.Label htmlFor='numberOfChildren'>Children:</Form.Label>
              <FormControl
              required
              type='number'
              id='numberOfChildren'
              name='numberOfChildren'
              value={booking.numberOfChildren}
              placeholder='0'
              onChange={handleInputChange}/>                      
                  </div>
                </div>
              </fieldset>
              
              <div className='form-group mt-2 mb-2'>
                <button type='submit' className='btn btn-hotel'>Countinue</button>
              </div>
            </Form>
          </div>
        </div>
        <div className='col-md-6'>
          {isSubmitted && (
            <BookingSummary
            booking={booking} payment={calculatePayment}
            isFormValid={isValidated} onConfirm={handleBooking}/>
          )}
        </div>
      </div>      
    </div>  
    </>
  )
}

export default BookingForm
