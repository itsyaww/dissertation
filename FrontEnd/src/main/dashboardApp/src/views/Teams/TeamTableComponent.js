import TeamServices from "../../services/TeamServices";
import React from "react";
import GridItem from "../../components/Grid/GridItem";
import Card from "../../components/Card/Card";
import CardHeader from "../../components/Card/CardHeader";

import GridContainer from "../../components/Grid/GridContainer";

// @material-ui/icons
import {AccountBalance, AssignmentLate, DateRange, LocalOffer, Update, NotificationImportant, Warning} from "@material-ui/icons";
import CardBody from "../../components/Card/CardBody";
import Table from "../../components/Table/Table";

export default class TeamTableComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {teams : []
        };
    }

    componentDidMount = async () => {

        const teams = await TeamServices.getTeams();
        const result = [];

        teams.forEach((team) => (result.push([team.teamID, team.teamName, team.teamPrimaryManager, team.teamSecondaryManager])));
        console.log(result);

        this.setState({
            teams:result});
    };

    render(){

        const teams = this.state.teams;

        return(
            <GridContainer>
                <GridItem xs={12} sm={12} md={12}>
                    <Card>
                        <CardHeader color="primary">
                            <h4 className={this.props.classes.cardTitleWhite}>Overview of Teams Stored in the Graph</h4>
                        </CardHeader>
                        <CardBody>
                            <Table
                                tableHeaderColor="primary"
                                tableHead={["Team ID", "Team Name", "Primary Manager", "Secondary Manager"]}
                                tableData={teams}
                            />
                        </CardBody>
                    </Card>
                </GridItem>
            </GridContainer>

        );};
}
