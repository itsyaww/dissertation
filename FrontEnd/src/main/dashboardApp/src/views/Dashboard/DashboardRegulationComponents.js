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
        incomingRegulation :0,
        riskbusinessUnits :0,
        supervisoryBodies: 0};
    }

    componentDidMount() {
        this.state.goingLiveRegulations = DashboardServices.getGoingLiveRegulations();
        this.state.incomingRegulation = DashboardServices.getIncomingRegulations();
        this.state.riskbusinessUnits = DashboardServices.getAtRiskBusinessUnits();
        this.state.supervisoryBodies = DashboardServices.getUniqueSupervisorBodyCount();
    }

    render(){
        const liveReg = (<h3 className={this.props.classes.cardTitle}>{this.state.goingLiveRegulations}</h3>);
        //const liveReg = 49;
        const incomingReg = (<h3 className={this.props.classes.cardTitle}>{this.state.incomingRegulation}</h3>)
        //const incomingReg = 15;
        const riskBU = (<h3 className={this.props.classes.cardTitle}>{this.state.riskbusinessUnits}</h3>)
        //const riskBU = 23;
        const supBody = (<h3 className={this.props.classes.cardTitle}>{this.state.supervisoryBodies}</h3>)
        //const supBody = 10;

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
                                <Danger>
                                    <Warning />
                                </Danger>
                                <a href="#pablo" onClick={e => e.preventDefault()}>
                                    Get more space
                                </a>
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
