webpackJsonp([1],{223:function(n,l,t){"use strict";function e(n){return u._19(0,[u._16(402653184,1,{mapEl:0}),(n()(),u.Z(1,0,null,null,10,"ion-header",[],null,null,null,null,null)),u.Y(2,16384,null,0,v.a,[m.a,u.j,u.z,[2,k.a]],null,null),(n()(),u._18(-1,null,["\n  "])),(n()(),u.Z(4,0,null,null,6,"ion-navbar",[["class","toolbar"]],[[8,"hidden",0],[2,"statusbar-padding",null]],null,null,h.b,h.a)),u.Y(5,49152,null,0,w.a,[C.a,[2,k.a],[2,j.a],m.a,u.j,u.z],null,null),(n()(),u._18(-1,3,["\n    "])),(n()(),u.Z(7,0,null,3,2,"ion-title",[],null,null,null,y.b,y.a)),u.Y(8,49152,null,0,A.a,[m.a,u.j,u.z,[2,E.a],[2,w.a]],null,null),(n()(),u._18(-1,0,["ArcGis"])),(n()(),u._18(-1,3,["\n  "])),(n()(),u._18(-1,null,["\n"])),(n()(),u._18(-1,null,["\n\n"])),(n()(),u.Z(13,0,null,null,4,"ion-content",[],[[2,"statusbar-padding",null],[2,"has-refresher",null]],null,null,S.b,S.a)),u.Y(14,4374528,null,0,P.a,[m.a,Z.a,q.a,u.j,u.z,C.a,I.a,u.u,[2,k.a],[2,j.a]],null,null),(n()(),u._18(-1,1,["\n  "])),(n()(),u.Z(16,0,null,1,0,"div",[["id","mapDiv"]],null,null,null,null,null)),(n()(),u._18(-1,1,["\n"]))],null,function(n,l){n(l,4,0,u._11(l,5)._hidden,u._11(l,5)._sbPadding);n(l,13,0,u._11(l,14).statusbarPadding,u._11(l,14)._hasRefresher)})}Object.defineProperty(l,"__esModule",{value:!0});var u=t(0),o=(t(7),t(106),t(414)),a=function(){function n(n){var l=this;this.platform=n,this.mapView=null,n.ready().then(function(){l.initMap()})}return n.prototype.initMap=function(){var n=this,l=0,t=0;return navigator.geolocation.watchPosition(function(e){l=e.coords.latitude,t=e.coords.longitude,null!=n.mapView&&(console.log("Centering map: "+l+", "+t),n.mapView.center=[t,l])},function(n){switch(n.code){case n.PERMISSION_DENIED:console.error("User denied the request for Geolocation.");break;case n.POSITION_UNAVAILABLE:console.error("Location information is unavailable.");break;case n.TIMEOUT:console.error("The request to get user location timed out."),alert("Unable to start geolocation. Check application settings.")}},{enableHighAccuracy:!0,timeout:6e4}),Object(o.loadModules)(["esri/config","esri/Map","esri/views/MapView","esri/layers/WMTSLayer","esri/widgets/LayerList","dojo/domReady!"]).then(function(l){var t=l[1],e=l[2];console.log("Geo: starting map");var u=new t({basemap:"streets"});n.mapView=new e({container:"mapDiv",center:[15,65],zoom:4,map:u})}).catch(function(n){console.log("ArcGIS: "+n)})},n}(),r=function(){return function(){}}(),i=t(181),s=t(182),c=t(183),d=t(184),f=t(185),b=t(186),_=t(187),p=t(188),g=t(189),v=t(110),m=t(1),k=t(4),h=t(309),w=t(38),C=t(8),j=t(20),y=t(310),A=t(108),E=t(52),S=t(250),P=t(21),Z=t(3),q=t(9),I=t(34),L=u.X({encapsulation:2,styles:[],data:{}}),M=u.V("page-arcgis",a,function(n){return u._19(0,[(n()(),u.Z(0,0,null,null,1,"page-arcgis",[],null,null,null,e,L)),u.Y(1,49152,null,0,a,[Z.a],null,null)],null,null)},{},{},[]),Y=t(15),T=t(16),x=t(107),z=t(35);t.d(l,"ArcgisPageModuleNgFactory",function(){return O});var O=u.W(r,[],function(n){return u._7([u._8(512,u.i,u.S,[[8,[i.a,s.a,c.a,d.a,f.a,b.a,_.a,p.a,g.a,M]],[3,u.i],u.s]),u._8(4608,Y.k,Y.j,[u.r,[2,Y.s]]),u._8(4608,T.k,T.k,[]),u._8(4608,T.c,T.c,[]),u._8(512,Y.b,Y.b,[]),u._8(512,T.j,T.j,[]),u._8(512,T.d,T.d,[]),u._8(512,T.i,T.i,[]),u._8(512,x.a,x.a,[]),u._8(512,x.b,x.b,[]),u._8(512,r,r,[]),u._8(256,z.a,a,[])])})},250:function(n,l,t){"use strict";function e(n){return u._19(2,[u._16(402653184,1,{_fixedContent:0}),u._16(402653184,2,{_scrollContent:0}),(n()(),u.Z(2,0,[[1,0],["fixedContent",1]],null,1,"div",[["class","fixed-content"]],null,null,null,null,null)),u._10(null,0),(n()(),u.Z(4,0,[[2,0],["scrollContent",1]],null,1,"div",[["class","scroll-content"]],null,null,null,null,null)),u._10(null,1),u._10(null,2)],null,null)}t.d(l,"a",function(){return o}),l.b=e;var u=t(0),o=(t(1),t(3),t(9),t(34),t(4),t(20),u.X({encapsulation:2,styles:[],data:{}}))},309:function(n,l,t){"use strict";function e(n){return u._19(0,[(n()(),u.Z(0,0,null,null,1,"div",[["class","toolbar-background"]],null,null,null,null,null)),u.Y(1,278528,null,0,o.g,[u.p,u.q,u.j,u.A],{klass:[0,"klass"],ngClass:[1,"ngClass"]},null),(n()(),u.Z(2,0,null,null,8,"button",[["class","back-button"],["ion-button","bar-button"]],[[8,"hidden",0]],[[null,"click"]],function(n,l,t){var e=!0;if("click"===l){e=!1!==n.component.backButtonClick(t)&&e}return e},a.b,a.a)),u.Y(3,278528,null,0,o.g,[u.p,u.q,u.j,u.A],{klass:[0,"klass"],ngClass:[1,"ngClass"]},null),u.Y(4,1097728,null,0,r.a,[[8,"bar-button"],i.a,u.j,u.z],null,null),(n()(),u.Z(5,0,null,0,2,"ion-icon",[["class","back-button-icon"],["role","img"]],[[2,"hide",null]],null,null,null,null)),u.Y(6,278528,null,0,o.g,[u.p,u.q,u.j,u.A],{klass:[0,"klass"],ngClass:[1,"ngClass"]},null),u.Y(7,147456,null,0,s.a,[i.a,u.j,u.z],{name:[0,"name"]},null),(n()(),u.Z(8,0,null,0,2,"span",[["class","back-button-text"]],null,null,null,null,null)),u.Y(9,278528,null,0,o.g,[u.p,u.q,u.j,u.A],{klass:[0,"klass"],ngClass:[1,"ngClass"]},null),(n()(),u._18(10,null,["",""])),u._10(null,0),u._10(null,1),u._10(null,2),(n()(),u.Z(14,0,null,null,2,"div",[["class","toolbar-content"]],null,null,null,null,null)),u.Y(15,278528,null,0,o.g,[u.p,u.q,u.j,u.A],{klass:[0,"klass"],ngClass:[1,"ngClass"]},null),u._10(null,3)],function(n,l){var t=l.component;n(l,1,0,"toolbar-background","toolbar-background-"+t._mode);n(l,3,0,"back-button","back-button-"+t._mode);n(l,6,0,"back-button-icon","back-button-icon-"+t._mode);n(l,7,0,t._bbIcon);n(l,9,0,"back-button-text","back-button-text-"+t._mode);n(l,15,0,"toolbar-content","toolbar-content-"+t._mode)},function(n,l){var t=l.component;n(l,2,0,t._hideBb);n(l,5,0,u._11(l,7)._hidden);n(l,10,0,t._backText)})}t.d(l,"a",function(){return c}),l.b=e;var u=t(0),o=t(15),a=t(40),r=t(18),i=t(1),s=t(39),c=(t(4),t(20),u.X({encapsulation:2,styles:[],data:{}}))},310:function(n,l,t){"use strict";function e(n){return u._19(2,[(n()(),u.Z(0,0,null,null,2,"div",[["class","toolbar-title"]],null,null,null,null,null)),u.Y(1,278528,null,0,o.g,[u.p,u.q,u.j,u.A],{klass:[0,"klass"],ngClass:[1,"ngClass"]},null),u._10(null,0)],function(n,l){n(l,1,0,"toolbar-title","toolbar-title-"+l.component._mode)},null)}t.d(l,"a",function(){return a}),l.b=e;var u=t(0),o=t(15),a=(t(1),u.X({encapsulation:2,styles:[],data:{}}))},414:function(n,l,t){!function(n,t){t(l)}(0,function(n){"use strict";function l(n,l,t){var e;t&&(e=function(n,l){var t=function(e){l(e.error||new Error("There was an error attempting to load "+n.src)),n.removeEventListener("error",t,!1)};return n.addEventListener("error",t,!1),t}(n,t));var u=function(){l(n),n.removeEventListener("load",u,!1),e&&n.removeEventListener("error",e,!1)};n.addEventListener("load",u,!1)}function t(){return document.querySelector("script[data-esri-loader]")}function e(){var n=window.require;return n&&n.on}function u(n){var l=function(n){return document.querySelector('link[href*="'+n+'"]')}(n);return l||(l=function(n){var l=document.createElement("link");return l.rel="stylesheet",l.href=n,l}(n),document.head.appendChild(l)),l}function o(n){return void 0===n&&(n={}),n.url||(n.url=s),new c.Promise(function(o,a){var r=t();if(r){var s=r.getAttribute("src");s!==n.url?a(new Error("The ArcGIS API for JavaScript is already loaded ("+s+").")):e()?o(r):l(r,o,a)}else e()?a(new Error("The ArcGIS API for JavaScript is already loaded.")):(n.css&&u(n.css),n.dojoConfig&&(window.dojoConfig=n.dojoConfig),r=function(n){var l=document.createElement("script");return l.type="text/javascript",l.src=n,l.setAttribute("data-esri-loader","loading"),l}(n.url),i=n.url,l(r,function(){r.setAttribute("data-esri-loader","loaded"),o(r)},a),document.body.appendChild(r))})}function a(n){return new c.Promise(function(l,t){var e=window.require.on("error",t);window.require(n,function(){for(var n=[],t=0;t<arguments.length;t++)n[t]=arguments[t];e.remove(),l(n)})})}function r(n,l){return void 0===l&&(l={}),e()?a(n):(!l.url&&i&&(l.url=i),o(l).then(function(){return a(n)}))}var i,s="https://js.arcgis.com/4.6/",c={Promise:"undefined"!=typeof window?window.Promise:void 0},d={getScript:t,isLoaded:e,loadModules:r,loadScript:o,utils:c};n.utils=c,n.getScript=t,n.isLoaded=e,n.loadCss=u,n.loadScript=o,n.loadModules=r,n.default=d,Object.defineProperty(n,"__esModule",{value:!0})})}});