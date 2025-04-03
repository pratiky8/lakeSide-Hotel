//rafc
import React, { useEffect, useState } from 'react'
import { getRoomTypes } from '../utils/ApiFunctions'

const RoomTypeSelector = ({handleRoomInputChange, newRoom}) => {
const [roomType, setRoomType]=useState([""])
const [showNewRoomTypeInput, setShowNewRoomTypeInput ]=useState(false)
const [newRoomType, setNewRoomType]=useState("")

  //Fetch Room Types when component mounts
useEffect(()=>{
    getRoomTypes().then((data)=>{
        setRoomType(data)
    })
},[])




   
    //  Handle New Room Type Input
const handleNewRoomTypeInputChange=(e)=>{
    setNewRoomType(e.target.value);
}

  //  Add new room type to the list
const handleAddNewRoomType=(e)=>{   
    if(newRoomType!== ""){
        setRoomType([newRoomType, ...roomType])
        setNewRoomType("")
        setShowNewRoomTypeInput(false)
    }
}

  return (
    <>
     { roomType.length > 0 && (
        <div>
            <select 
            name="roomType" 
            id="roomType" 
            value={newRoom.roomType}
            required
            onChange={(e)=>{
                if(e.target.value ==="Add New"){
                    setShowNewRoomTypeInput(true)
                }else{
                    handleRoomInputChange(e)
                }
            }}>
                <option value={""}>select a room type</option>
                <option value={"Add New"}>Add New</option>
                {roomType.map((type, index)=>(
                    <option key={index} value={type} >  
                        {type}  
                    </option>
                ))}
            </select>

            {showNewRoomTypeInput && (
                <div className='input-group'>
                    <input 
                    type="text"
                    className='form-control'
                    placeholder='Enter a new room type'
                    onChange={handleNewRoomTypeInputChange}
                     />
                     <button className='btn btn-hotel' type='button' onClick={handleAddNewRoomType}>
                        Add                        
                     </button>
                </div>
            )}
        </div>
     )}  
      
    </>
  )
}

export default RoomTypeSelector

