import BusinessUnitServices from "../../services/BusinessUnitServices";
import React from "react";
import GridItem from "../../components/Grid/GridItem";
import Card from "../../components/Card/Card";
import CardHeader from "../../components/Card/CardHeader";
import GridContainer from "../../components/Grid/GridContainer";
import CardBody from "../../components/Card/CardBody";
import Table from "../../components/Table/Table";

export default class BusinessUnitTableComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {businessUnits : []
        };
    }

    componentDidMount = async () => {

        const bus = await BusinessUnitServices.getBusinessUnits();
        const result = [];

        bus.forEach((bu) => (result.push([bu.buID, bu.buName, bu.buSeniorLead, bu.buComplianceOfficer])));
        console.log(result);

        this.setState({
            businessUnits:result});
    };

    render(){

        const bus = this.state.businessUnits;

        return(
            <GridContainer>
                <GridItem xs={12} sm={12} md={12}>
                    <Card>
                        <CardHeader color="info">
                            <h4 className={this.props.classes.cardTitleWhite}>Overview of BUs Stored in the Graph</h4>
                        </CardHeader>
                        <CardBody>
                            <Table
                                tableHeaderColor="info"
                                tableHead={["Business Unit ID", "Business Unit Name", "Senior Lead", "Compliance Officer"]}
                                tableData={bus}
                            />
                        </CardBody>
                    </Card>
                </GridItem>
            </GridContainer>

        );};
}
