import React from 'react'
import styles from "./TableEntry.module.scss"

const TableEntry = (props) => {

const { car } = props;

    return (
        <>
        <td>{car.make}</td>
        <td>{car.model}</td>
        <td>{car.productionYear}</td>
        <td>{car.numberPlate}</td>
        <td>{car.coverType}</td>
        <td>{car.driverName1}</td>
        <td>{car.driverName2}</td>
        <td>{car.driverName3}</td>
        <td>{car.driverName4}</td>
        <td>{car.excess}</td>
        <td>{car.noClaimsApplied}</td>
        </>
    )
}

export default TableEntry
