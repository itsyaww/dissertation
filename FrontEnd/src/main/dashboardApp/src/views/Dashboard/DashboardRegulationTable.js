import DashboardServices from "../../services/DashboardServices";
import React from "react";
import GridItem from "../../components/Grid/GridItem";
import Card from "../../components/Card/Card";
import CardHeader from "../../components/Card/CardHeader";

import GridContainer from "../../components/Grid/GridContainer";

// @material-ui/icons
import {AccountBalance, AssignmentLate, DateRange, LocalOffer, Update, NotificationImportant, Warning} from "@material-ui/icons";
import CardBody from "../../components/Card/CardBody";
import Table from "../../components/Table/Table";

export default class DashboardRegulationTable extends React.Component {
    constructor(props) {
        super(props);

        this.state = {regulations : []
        };
    }

    componentDidMount = async () => {

        const regs = await DashboardServices.getAllRegulations();
        const result = [];

        regs.forEach((reg) => (result.push([reg.regulationID, reg.regulationCode, reg.regulationTitle, reg.dateIssued, reg.goLive, reg.atRisk])));
        console.log(result);

        this.setState({
            regulations:result});
    };

    render(){

        const regs = this.state.regulations;

        return(
            <GridContainer>
                <GridItem xs={12} sm={12} md={12}>
                    <Card>
                        <CardHeader color="primary">
                            <h4 className={this.props.classes.cardTitleWhite}>Regulation Stats</h4>
                            <p className={this.props.classes.cardCategoryWhite}>
                                Recently processed regulations
                            </p>
                        </CardHeader>
                        <CardBody>
                            <Table
                                tableHeaderColor="primary"
                                tableHead={["Regulation ID", "Regulation Code", "Title", "Date Issued","Date Go-Live","At Risk"]}
                                tableData={regs}
                            />
                        </CardBody>
                    </Card>
                </GridItem>
            </GridContainer>

        );};
}
