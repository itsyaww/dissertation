import React from "react";
// react plugin for creating charts
import ChartistGraph from "react-chartist";
// @material-ui/core
import { makeStyles } from "@material-ui/core/styles";
// core ../../components
import GridItem from "../../components/Grid/GridItem.js";
import GridContainer from "../../components/Grid/GridContainer.js";
import Table from "../../components/Table/Table.js";
import Card from "../../components/Card/Card.js";
import CardHeader from "../../components/Card/CardHeader.js";
import CardBody from "../../components/Card/CardBody.js";

import styles from "../../assets/jss/material-dashboard-react/views/dashboardStyle.js";
import DashboardRegulationComponents from "./DashboardRegulationComponents";
import DashboardRegulationTable from "./DashboardRegulationTable";

const useStyles = makeStyles(styles);

export default function Dashboard() {
  const classes = useStyles();
  return (
    <div>
      <DashboardRegulationComponents classes={classes}/>
        <DashboardRegulationTable classes={classes}/>
    </div>
  );
}
