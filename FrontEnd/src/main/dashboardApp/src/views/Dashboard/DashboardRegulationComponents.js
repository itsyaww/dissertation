import DashboardServices from "../../services/DashboardServices";
import React from "react";
import GridItem from "../../components/Grid/GridItem";
import Card from "../../components/Card/Card";
import CardHeader from "../../components/Card/CardHeader";
import CardIcon from "../../components/Card/CardIcon";
import CardFooter from "../../components/Card/CardFooter";
import Danger from "../../components/Typography/Danger";
import GridContainer from "../../components/Grid/GridContainer";

// @material-ui/icons
import {AccountBalance, AssignmentLate, DateRange, LocalOffer, Update, NotificationImportant, Warning} from "@material-ui/icons";

export default class DashboardRegulationComponents extends React.Component {
    constructor(props) {
        super(props);

        this.state = {goingLiveRegulations: 0,
        incomingRegulation : 0,
        riskbusinessUnits : 0,
        supervisoryBodies: 0};
    }

    componentDidMount = async () => {

        const liveReg = await DashboardServices.getIncomingRegulations();
        const incomingReg = await DashboardServices.getIncomingRegulations();
        const riskBU = await DashboardServices.getAtRiskBusinessUnits();
        const supBody = await DashboardServices.getUniqueSupervisorBodyCount();

        this.setState({
            goingLiveRegulations:liveReg,
            incomingRegulation :incomingReg,
            riskbusinessUnits :riskBU,
            supervisoryBodies :supBody});
    };

    render(){

        const liveReg = this.state.goingLiveRegulations;
        const incomingReg = this.state.incomingRegulation;
        const riskBU = this.state.riskbusinessUnits;
        const supBody = this.state.supervisoryBodies;

        return(
            <GridContainer>
                <GridItem xs={12} sm={6} md={3}>
                    <Card>
                        <CardHeader color="warning" stats icon>
                            <CardIcon color="warning">
                                <NotificationImportant />
                            </CardIcon>
                            <p className={this.props.classes.cardCategory}>Regulations Nearing Go-Live Date</p>
                            <h3 className={this.props.classes.cardTitle}>{liveReg}</h3>
                        </CardHeader>
                        <CardFooter stats>
                            <div className={this.props.classes.stats}>
                                <Warning />
                                Attention Required
                            </div>
                        </CardFooter>
                    </Card>
                </GridItem>
                <GridItem xs={12} sm={6} md={3}>
                    <Card>
                        <CardHeader color="success" stats icon>
                            <CardIcon color="success">
                                <AssignmentLate />
                            </CardIcon>
                            <p className={this.props.classes.cardCategory}>Incoming Regulations</p>
                            <h3 className={this.props.classes.cardTitle}>{incomingReg}</h3>
                        </CardHeader>
                        <CardFooter stats>
                            <div className={this.props.classes.stats}>
                                <DateRange />
                                Last Year
                            </div>
                        </CardFooter>
                    </Card>
                </GridItem>
                <GridItem xs={12} sm={6} md={3}>
                    <Card>
                        <CardHeader color="danger" stats icon>
                            <CardIcon color="danger">
                                <Warning />
                            </CardIcon>
                            <p className={this.props.classes.cardCategory}>Business Units at Risk</p>
                            <h3 className={this.props.classes.cardTitle}>{riskBU}</h3>
                        </CardHeader>
                        <CardFooter stats>
                            <div className={this.props.classes.stats}>
                                <LocalOffer />
                                Tracked from Github
                            </div>
                        </CardFooter>
                    </Card>
                </GridItem>
                <GridItem xs={12} sm={6} md={3}>
                    <Card>
                        <CardHeader color="info" stats icon>
                            <CardIcon color="info">
                                <AccountBalance />
                            </CardIcon>
                            <p className={this.props.classes.cardCategory}>Supervisory Bodies</p>
                            <h3 className={this.props.classes.cardTitle}>{supBody}</h3>
                        </CardHeader>
                        <CardFooter stats>
                            <div className={this.props.classes.stats}>
                                <Update />
                                Just Updated
                            </div>
                        </CardFooter>
                    </Card>
                </GridItem>
            </GridContainer>
        );};
}
