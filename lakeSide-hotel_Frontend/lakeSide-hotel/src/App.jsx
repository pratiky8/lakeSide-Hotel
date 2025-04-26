import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import AddRoom from './components/room/AddRoom'
import ExistingRooms from './components/room/ExistingRooms'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Home from './components/home/Home'
import EditRoom from './components/room/EditRoom'
import Footer from './components/layout/Footer'
import NavBar from './components/layout/NavBar'
import RoomListing from './components/room/RoomListing'


function App() {
  const [count, setCount] = useState(0)

  return (
    <>

    <main>
       {/* <Router> */}
        <NavBar/>
        <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/edit-room/:roomId" element={<EditRoom/>}/>
          <Route path="/existing-rooms" element={<ExistingRooms/>}/>
          <Route path="/add-room" element={<AddRoom/>}/>
          <Route path="/browse-all-rooms" element={<RoomListing/>}/>

        </Routes>
       <Footer/>

      {/* </Router>        */}
    </main>
    
    
     
    </>
  )
}

export default App
