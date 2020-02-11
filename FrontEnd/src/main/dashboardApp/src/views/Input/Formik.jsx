import React from 'react'
import {withFormik} from 'formik'
import Input from "./Input";
import {fileUpload} from "../../services/inputServices";

const FormikHOC = withFormik({
    mapPropsToValues: () => ({
        "files":[],
        "responseData": ""
    }),

    handleSubmit: (values, actions) => {
        let response = fileUpload(values.files);
        actions.setFieldValue("responseData", response);
    },



    displayName: 'FormikHOC',
})(Input);


export default FormikHOC;




