import React, {useState} from 'react';
// @material-ui/core components
import {makeStyles} from "@material-ui/core/styles";
// core components
import RegulationListComponent from "./RegulationListComponent";

const styles = {
    cardCategoryWhite: {
        "&,& a,& a:hover,& a:focus": {
            color: "rgba(255,255,255,.62)",
            margin: "0",
            fontSize: "14px",
            marginTop: "0",
            marginBottom: "0"
        },
        "& a,& a:hover,& a:focus": {
            color: "#FFFFFF"
        }
    },
    cardTitleWhite: {
        color: "#FFFFFF",
        marginTop: "0px",
        minHeight: "auto",
        fontWeight: "300",
        fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
        marginBottom: "3px",
        textDecoration: "none",
        "& small": {
            color: "#777",
            fontSize: "65%",
            fontWeight: "400",
            lineHeight: "1"
        }
    }
};

const useStyles = makeStyles(styles);

export default function RegulationOverview() {
    const classes = useStyles();
    const [file, setFile] = useState('/myPDF.pdf');

    function updateFile(filePath)
    {
        console.log("File state updated to: " + filePath);
        setFile(filePath);
    }

    function renderViewer()
    {
        console.log("Rendering viewer...");
        console.log(file);

        return (
            <div id='viewer' style={{ width: '1080px', height: '720px' }}>
                <iframe src ={"/pdfjs-2.3.200-dist/web/viewer.html?file=" + file} width ="100%" height= "100%">
                    Browser error.
                </iframe>
            </div>
        )
    }

    return (
        <div>
            <RegulationListComponent classes={classes} updateFile={(filePath) => updateFile(filePath)}/>
            {renderViewer()}
        </div>);
}
