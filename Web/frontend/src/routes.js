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
import Home from "views/home.js";
import Mapa from "views/mapa.js";
import Lotacao from "views/lotacao.js";
import Alertas from "views/alertas.js";
import Report from "views/reports.js";
import Opinioes from "views/opinioes.js";
import PartilharInfo from "views/partilharInfo.js";
import Utilizadores from "views/utilizadores.js";
import Definicoes from "views/definicoes.js";
const dashboardRoutes = [ //abrir paginas da aplicacao no menu da nossa dashboard
  {
    path: "/home",
    name: "Home",
    icon: "nc-icon nc-app",
    component: Home,
    layout: "/admin",
  },
  {
    path: "/mapa",
    name: "Mapa",
    icon: "nc-icon nc-map-big",
    component: Mapa,
    layout: "/admin",
  },
  {
    path: "/lotacao",
    name: "Lotação",
    icon: "nc-icon nc-circle-09",
    component: Lotacao,
    layout: "/admin",
  },
  {
    path: "/alertas",
    name: "Alertas",
    icon: "nc-icon nc-bell-55",
    component: Alertas,
    layout: "/admin",
  },
  {
    path: "/reports",
    name: "Reports",
    icon: "nc-icon nc-square-pin",
    component: Report,
    layout: "/admin",
  },
  {
    path: "/opinioes",
    name: "Opiniões",
    icon: "nc-icon nc-paper-2",
    component: Opinioes,
    layout: "/admin",
  },
  {
    path: "/partilharinfo",
    name: "Partilhar Info",
    icon: "nc-icon nc-send",
    component: PartilharInfo,
    layout: "/admin",
  },
  {
    path: "/utilizadores",
    name: "Utilizadores",
    icon: "nc-icon nc-bell-55",
    component: Utilizadores,
    layout: "/admin",
  },
  {
    path: "/definicoes",
    name: "Definições",
    icon: "nc-icon nc-preferences-circle-rotate",
    component: Definicoes,
    layout: "/admin",
  },
];

export default dashboardRoutes;
