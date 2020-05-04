import FileServices from "../../services/FileServices";
import React from "react";
import GridItem from "../../components/Grid/GridItem";
import Card from "../../components/Card/Card";
import CardHeader from "../../components/Card/CardHeader";

import GridContainer from "../../components/Grid/GridContainer";
// @material-ui/icons
import CardBody from "../../components/Card/CardBody";
import ClickableList from "./List";

export default class RegulationListComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {fileNames : []
        };
    }

    componentDidMount = async () => {

        const fileNames = await FileServices.getFiles();
        const result = [];

        fileNames.forEach((file) => (result.push([file])));
        console.log(result);

        this.setState({
            fileNames:result});
    };

    render(){

        const fileNames = this.state.fileNames;

        const {classes, updateFile} = this.props;
        return(
            <GridContainer>
                <GridItem xs={12} sm={12} md={12}>
                    <Card>
                        <CardHeader color="info">
                            <h4 className={classes.cardTitleWhite}>Processed Regulations</h4>
                        </CardHeader>
                        <CardBody>
                            <ClickableList listHeaderColor="info"
                                           updateFileFunction={updateFile}
                                           listData={fileNames}/>
                        </CardBody>
                    </Card>
                </GridItem>
            </GridContainer>

        );};
}
