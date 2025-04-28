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

























// // rafc
// import React, { useEffect, useState } from 'react';
// import { getRoomTypes } from '../utils/ApiFunctions';

// const RoomTypeSelector = ({ handleRoomInputChange, newRoom }) => {
//   const [roomTypes, setRoomTypes] = useState([]);  // changed from [""] to []
//   const [showNewRoomTypeInput, setShowNewRoomTypeInput] = useState(false);
//   const [newRoomType, setNewRoomType] = useState("");

//   // Fetch Room Types when component mounts
//   useEffect(() => {
//     getRoomTypes()
//       .then((data) => {
//         console.log("Fetched room types:", data); // 👈 add console for checking
//         setRoomTypes(data || []); // if data is null, set empty array
//       })
//       .catch((error) => console.error("Error fetching room types:", error));
//   }, []);

//   // Handle New Room Type Input
//   const handleNewRoomTypeInputChange = (e) => {
//     setNewRoomType(e.target.value);
//   };

//   // Add new room type to the list
//   const handleAddNewRoomType = () => {
//     if (newRoomType.trim() !== "") {
//       setRoomTypes([newRoomType, ...roomTypes]);
//       setNewRoomType("");
//       setShowNewRoomTypeInput(false);
//     }
//   };

//   return (
//     <>
//       <div>
//         {/* Dropdown */}
//         <select
//           name="roomType"
//           id="roomType"
//           value={newRoom.roomType}
//           required
//           onChange={(e) => {
//             if (e.target.value === "Add New") {
//               setShowNewRoomTypeInput(true);
//             } else {
//               handleRoomInputChange(e);
//               setShowNewRoomTypeInput(false); // reset input when normal room type selected
//             }
//           }}
//         >
//           <option value="">Select a room type</option>
//           <option value="Add New">➕ Add New Room Type</option>
//           {roomTypes.map((type, index) => (
//             <option key={index} value={type}>
//               {type}
//             </option>
//           ))}
//         </select>

//         {/* New Room Type Input */}
//         {showNewRoomTypeInput && (
//           <div className="input-group mt-2">
//             <input
//               type="text"
//               className="form-control"
//               placeholder="Enter a new room type"
//               value={newRoomType}
//               onChange={handleNewRoomTypeInputChange}
//             />
//             <button
//               className="btn btn-hotel"
//               type="button"
//               onClick={handleAddNewRoomType}
//             >
//               Add
//             </button>
//           </div>
//         )}
//       </div>
//     </>
//   );
// };

// export default RoomTypeSelector;
