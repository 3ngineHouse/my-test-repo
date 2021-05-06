import React, { useState, useEffect }  from 'react'
import styles from "./App.module.scss";
import NavBar from "./components/NavBar";
import CarTable from "./components/CarTable";
import AddCarPanel from "./components/AddCarPanel";

const App = () => {

  const[customerList, setCustomerList] = useState([])
  const[selectedCustomer, setSelectedCustomer] = useState(2)
  const[showNewCar, setShowNewCar] = useState(false)
    
  const getCustomerList = () => {
      fetch("https://james-spring-dot-greg-312709.nw.r.appspot.com/get/customers")
      .then(res => res.json())
      .then(json => setCustomerList(json))
      .catch(err => console.log(err))
  }

  useEffect(() => {
    getCustomerList()
  }, [])

  const getListItemJSX = (customer) => (
    <option key={customer.id} data-id={customer.id}>{customer.name}</option>
  );

  const getSelectedCustomerID = (event) => {
    for (let option of event.target.children) {
      if (option.value === event.target.value) {
        setSelectedCustomer(option.getAttribute("data-id"))
      }
    }
  }

  const toggleShowNewCar = () => {
    setShowNewCar(!showNewCar)
  }

  return (
    <>
      <header className={styles.nav}>
        <nav className={styles.navFlex}>
          <label>Select account:</label>
          <select onChange={(event)=>getSelectedCustomerID(event)}>
              {customerList.map(getListItemJSX)}
          </select>
          <div>
              Selected customer:
              <span>{selectedCustomer}</span>
          </div>
        </nav>
      </header>
      <body className={styles.body}>
        <CarTable customerID={selectedCustomer}/>
      </body>
      <button onClick={()=>toggleShowNewCar()}>Add car</button>
      {showNewCar ? <AddCarPanel toggle={toggleShowNewCar} /> : null}
    </>
  )
}

export default App
