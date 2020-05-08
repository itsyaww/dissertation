import React from "react";

// @material-ui/core
import { makeStyles } from "@material-ui/core/styles";
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
