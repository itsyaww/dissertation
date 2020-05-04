/*!

=========================================================
* Material Dashboard React - v1.8.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/material-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
// @material-ui/icons
import Dashboard from "@material-ui/icons/Dashboard";
import Person from "@material-ui/icons/Person";
import Libraryadd from "@material-ui/icons/LibraryAdd";
import LibraryBooks from "@material-ui/icons/LibraryBooks";
import People from "@material-ui/icons/People";
import Module from "@material-ui/icons/ChromeReaderMode";
import BubbleChart from "@material-ui/icons/BubbleChart";
import LocationOn from "@material-ui/icons/LocationOn";
import Notifications from "@material-ui/icons/Notifications";
import Unarchive from "@material-ui/icons/Unarchive";
import Language from "@material-ui/icons/Language";
// core components/views for Admin layout
import DashboardPage from "./views/Dashboard/Dashboard.js";
import UserProfile from "./views/UserProfile/UserProfile.js";
import TableList from "./views/TableList/TableList.js";
import BusinessUnits from "./views/BusinessUnits/BusinessUnits";
import RegulationOverview from "./views/RegulationOverview/RegulationOverview";
import Teams from "./views/Teams/Teams";
import Modules from "./views/Modules/Modules";
import Typography from "./views/Typography/Typography.js";
import Icons from "./views/Icons/Icons.js";
import Input from "./views/Input/Input.js";
import Maps from "./views/Maps/Maps.js";
import NotificationsPage from "./views/Notifications/Notifications.js";
import FormikHOC from "./views/Input/Formik.jsx";

const dashboardRoutes = [
    {
        path: "/dashboard",
        name: "Dashboard",
        icon: Dashboard,
        component: DashboardPage,
        layout: "/admin"
    },
    {
        path: "/regulations",
        name: "Regulation Overview",
        icon: Module,
        component: RegulationOverview,
        layout: "/admin"
    },{
        path: "/modules",
        name: "Modules",
        icon: Module,
        component: Modules,
        layout: "/admin"
    },
    {
        path: "/businessUnits",
        name: "Business Units",
        icon: LibraryBooks,
        component: BusinessUnits,
        layout: "/admin"
    },
    {
        path: "/teams",
        name: "Teams",
        icon: People,
        component: Teams,
        layout: "/admin"
    },
    {
        path: "/input",
        name: "Input",
        icon: Libraryadd,
        component: FormikHOC,
        layout: "/admin"
    }
];

export default dashboardRoutes;
