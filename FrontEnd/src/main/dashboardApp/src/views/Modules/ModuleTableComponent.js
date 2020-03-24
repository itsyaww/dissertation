import ModuleServices from "../../services/ModuleServices";
import React from "react";
import GridItem from "../../components/Grid/GridItem";
import Card from "../../components/Card/Card";
import CardHeader from "../../components/Card/CardHeader";

import GridContainer from "../../components/Grid/GridContainer";

// @material-ui/icons
import {AccountBalance, AssignmentLate, DateRange, LocalOffer, Update, NotificationImportant, Warning} from "@material-ui/icons";
import CardBody from "../../components/Card/CardBody";
import Table from "../../components/Table/Table";

export default class ModuleTableComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {modules : []
        };
    }

    componentDidMount = async () => {

        const modules = await ModuleServices.getModules();
        const result = [];

        modules.forEach((module) => (result.push([module.moduleCode, module.moduleName])));
        console.log(result);

        this.setState({
            modules:result});
    };

    render(){

        const modules = this.state.modules;

        return(
            <GridContainer>
                <GridItem xs={12} sm={12} md={12}>
                    <Card>
                        <CardHeader color="warning">
                            <h4 className={this.props.classes.cardTitleWhite}>Overview of Modules Stored in the Graph</h4>
                        </CardHeader>
                        <CardBody>
                            <Table
                                tableHeaderColor="warning"
                                tableHead={["Module Code", "Module Name"]}
                                tableData={modules}
                            />
                        </CardBody>
                    </Card>
                </GridItem>
            </GridContainer>

        );};
}
