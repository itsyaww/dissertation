import React from "react";

// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";

// core components
import GridItem from "../../components/Grid/GridItem.js";
import GridContainer from "../../components/Grid/GridContainer.js";
import CustomInput from "../../components/CustomInput/CustomInput.js";
import Button from "../../components/CustomButtons/Button.js";
import Card from "../../components/Card/Card.js";
import CardHeader from "../../components/Card/CardHeader.js";

import CardBody from "../../components/Card/CardBody.js";
import CardFooter from "../../components/Card/CardFooter.js";

import FileUpload from "../../components/FileUpload/FileUpload";

const styles = {
    cardCategoryWhite: {
        color: "rgba(255,255,255,.62)",
        margin: "0",
        fontSize: "14px",
        marginTop: "0",
        marginBottom: "0"
    },
    cardTitleWhite: {
        color: "#FFFFFF",
        marginTop: "0px",
        minHeight: "auto",
        fontWeight: "300",
        fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
        marginBottom: "3px",
        textDecoration: "none"
    }
};

const useStyles = makeStyles(styles);

export default function Input(props){
    const classes = useStyles();
    return(<div>
        <GridContainer>
            <GridItem xs={12} sm={12} md={8}>
                <Card>
                    <CardHeader color="success">
                        <h4 className={classes.cardTitleWhite}>File Upload</h4>
                        <p className={classes.cardCategoryWhite}>Upload regulation PDF documents</p>
                    </CardHeader>
                    <CardBody>

                        <GridItem xs={12} sm={12} md={12}>
                            <FileUpload setFieldValue={props.setFieldValue}/>
                        </GridItem>

                    </CardBody>
                    <CardFooter>
                        <Button onClick={props.handleSubmit} color="success">Submit Files</Button>
                    </CardFooter>
                </Card>
            </GridItem>
        </GridContainer>
    </div>);
}