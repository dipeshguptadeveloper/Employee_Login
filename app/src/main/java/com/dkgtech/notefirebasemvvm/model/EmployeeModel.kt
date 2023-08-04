package com.dkgtech.notefirebasemvvm.model

data class EmployeeModel(
    val empImage: Int,
    val empName: String = "",
    val empDepartment: String = "",
    val empLocation: String = "",
    val empDesignation: String = "",
    val empCostCentre: String = "",
    val empEmail: String = "",
    val empMobile: String = "",
    val empManager: String = "",
)
