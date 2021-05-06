import React, { useState, useEffect } from 'react'
import styles from "./CarTable.module.scss"
import TableEntry from "../TableEntry";

const CarTable = (props) => {

    const customerID = props
    const[carList, setCarList] = useState([])
    
    const getCarList = () => {
        fetch(`https://james-spring-dot-greg-312709.nw.r.appspot.com/get/carsByCustomer/${customerID.customerID}`)
        .then(res => res.json())
        .then(json => setCarList(json))
        .catch(err => console.log(err))
    }
    
    useEffect(() => {
        getCarList()
    }, [customerID])

    const getTableLineJSX = (car) => (
        <tr className={styles.carRow} key={car.carID}>
            <TableEntry car={car} />
        </tr>
    );

    return (
      <table className={styles.tableBody}>
        <tr>
            <th>Make</th>
            <th>Model</th>
            <th>Production Year</th>
            <th>Number Plate</th>
            <th>Cover Type</th>
            <th>First named driver</th>
            <th>Second named driver</th>
            <th>Third named driver</th>
            <th>Forth named driver</th>
            <th>Excess</th>
            <th>No claims applied?</th>
        </tr>    
        {carList.map(getTableLineJSX)}
      </table>
    );
};

export default CarTable;