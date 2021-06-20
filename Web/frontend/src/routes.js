/*!

=========================================================
* Light Bootstrap Dashboard React - v2.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/light-bootstrap-dashboard-react
* Copyright 2020 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/light-bootstrap-dashboard-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import Dashboard from "views/Dashboard.js";
import UserProfile from "views/UserProfile.js";
import TableList from "views/TableList.js";
import Typography from "views/Typography.js";
import Icons from "views/Icons.js";
import Maps from "views/Maps.js";
import Notifications from "views/Notifications.js";

const dashboardRoutes = [ //abrir paginas da aplicacao
  {
    path: "/dashboard",
    name: "Home",
    icon: "nc-icon nc-app",
    component: Dashboard,
    layout: "/admin",
  },
  {
    path: "/maps",
    name: "Mapa",
    icon: "nc-icon nc-map-big",
    component: Maps,
    layout: "/admin",
  },
  {
    path: "/user",
    name: "Lotação",
    icon: "nc-icon nc-circle-09",
    component: UserProfile,
    layout: "/admin",
  },
  {
    path: "/table",
    name: "Alertas",
    icon: "nc-icon nc-bell-55",
    component: TableList,
    layout: "/admin",
  },
  {
    path: "/typography",
    name: "Reports",
    icon: "nc-icon nc-square-pin",
    component: Typography,
    layout: "/admin",
  },
  {
    path: "/icons",
    name: "Opiniões",
    icon: "nc-icon nc-paper-2",
    component: Icons,
    layout: "/admin",
  },
  {
    path: "/notifications",
    name: "Partilhar Info",
    icon: "nc-icon nc-send",
    component: Notifications,
    layout: "/admin",
  },
  {
    path: "/notifications",
    name: "Utilizadores",
    icon: "nc-icon nc-bell-55",
    component: Notifications,
    layout: "/admin",
  },
  {
    path: "/notifications",
    name: "Definições",
    icon: "nc-icon nc-preferences-circle-rotate",
    component: Notifications,
    layout: "/admin",
  },
];

export default dashboardRoutes;
