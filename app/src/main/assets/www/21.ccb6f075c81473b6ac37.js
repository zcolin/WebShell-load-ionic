(window.webpackJsonp=window.webpackJsonp||[]).push([[21],{"Fp/D":function(t,n,e){"use strict";e.r(n);var o=e("CcnG"),i=function(){return function(){}}(),a=e("pMnS"),r=e("Pr26"),l=e("Vv3U"),c=e("ZZ/e"),u=e("oBZk"),p=e("6blF"),s=function(){return function(){}}(),f=function(){return function(){}}(),h=function(){return function(){}}(),d=function(){return function(){}}(),g=function(){return function(){}}(),y=function(){return function(){}}(),w=function(){function t(){}return t.getPointsDistance=function(t,n){var e=new AMap.LngLat(t.lng,t.lat),o=new AMap.LngLat(n.lng,n.lat);return e.distance(o)},t.prototype.ngAfterContentInit=function(){this.loadMap()},t.prototype.loadMap=function(){this._mapView=new AMap.Map("amap-container",{resizeEnable:!0})},t.prototype.getMap=function(){return this._mapView},t.prototype.getBounds=function(){return this._mapView.getBounds()},t.prototype.getCenter=function(){return this._mapView.getCenter()},t.prototype.setZoom=function(t){this._mapView.setZoom(t)},t.prototype.setCity=function(t){this._mapView.setCity(t)},t.prototype.setCenter=function(t){this._mapView.setCenter(t)},t.prototype.setZoomAndCenter=function(t,n){this._mapView.setZoomAndCenter(t,n)},t.prototype.onClick=function(t){this._mapView.on("click",function(n){t&&t(n)})},t.prototype.addGeoCoderPlugin=function(){return new p.a(function(t){AMap.service("AMap.Geocoder",function(){t.next(new AMap.Geocoder),t.complete()})})},t.prototype.addSearchNearPlugin=function(t){var n=this;return new p.a(function(e){n._mapView.plugin("AMap.PlaceSearch",function(){var n=new AMap.placeSearch({city:t.city,citylimit:t.citylimit||!1,type:t.type,pageSize:t.pageSize||20,extensions:t.extensions||"base",pageIndex:t.pageIndex||1,panel:t.panel,atuoFitView:t.atuoFitView||!0,map:t.map});e.next(n),e.complete()})})},t.prototype.addControlBarPlugin=function(t){var n=this;return new p.a(function(e){n._mapView.plugin(["AMap.ControlBar"],function(){t=t||new f;var o=new AMap.ControlBar({position:t.position||{top:"10px",right:"10px"},showZoomBar:t.showZoomBar||!0,showControlButton:t.showControlButton||!0});n._mapView.addControl(o),e.next(o),e.complete()})})},t.prototype.addMapTypePlugin=function(t){var n=this;return new p.a(function(e){n._mapView.plugin(["AMap.MapType"],function(){t=t||new h;var o=new AMap.MapType({defaultType:t.defaultType||0,showTraffic:t.showTraffic||!0,showRoad:t.showRoad||!0});n._mapView.addControl(o),e.next(o),e.complete()})})},t.prototype.addScalePlugin=function(t){var n=this;return new p.a(function(e){n._mapView.plugin(["AMap.Scale"],function(){t=t||new s;var o=new AMap.Scale({position:t.position||"LB",offset:t.offset||new AMap.Pixel(5,5)});n._mapView.addControl(o),e.next(o),e.complete()})})},t.prototype.addToolBarPlugin=function(t){var n=this;return new p.a(function(e){n._mapView.plugin(["AMap.ToolBar"],function(){t=t||new d;var o=new AMap.ToolBar({offset:t.offset||new AMap.Pixel(3,85),position:t.position||"RB",ruler:t.ruler||!0,noIpLocate:t.noIpLocate||!1,locate:t.locate||!1,liteStyle:t.liteStyle||!1,direction:t.direction||!1,autoPosition:t.autoPosition||!1,locationMarker:t.locationMarker,useNative:t.useNative||!0});n._mapView.addControl(o),e.next(o),e.complete()})})},t.prototype.addLocationPlugin=function(t){var n=this;return new p.a(function(e){n._mapView.plugin("AMap.Geolocation",function(){t=t||new g;var o=new AMap.Geolocation({enableHighAccuracy:t.enableHighAccuracy||!0,timeout:t.timeout||1e4,maximumAge:t.maximumAge||0,convert:t.convert||!0,showButton:t.showButton||!0,buttonPosition:t.buttonPosition||"LB",buttonOffset:t.buttonOffset||new AMap.Pixel(5,5),showMarker:t.showMarker||!0,showCircle:t.showCircle||!0,panToLocation:t.panToLocation||!0,zoomToAccuracy:t.zoomToAccuracy||!0});n._mapView.addControl(o),e.next(o),e.complete()})})},t.prototype.switchToImg=function(){this.setLayers([new AMap.TileLayer.Satellite])},t.prototype.switchToVec=function(){this.setLayers([new AMap.TileLayer])},t.prototype.setLayers=function(t){this._mapView.setLayers(t)},t.prototype.add=function(t){this._mapView.add(t)},t.prototype.remove=function(t){this._mapView.remove(t)},t.prototype.cleanMap=function(){this._mapView.clearMap()},t.prototype.getMaker=function(t,n){var e=t.icon&&t.icon instanceof y?new AMap.Icon({size:t.icon.size,imageOffset:t.icon.imageOffset,image:t.icon.image,imageSize:t.icon.imageSize}):t.icon,o=new AMap.Marker({position:t.position,extData:t.extData,offset:t.offset||new AMap.Pixel(-20,-62),icon:e,content:t.content,clickable:!!n,draggable:t.draggable,zIndex:t.zIndex,angle:t.angle,autoRotation:t.autoRotation,label:t.label});return n&&o.on("click",n),o},t.prototype.addMaker=function(t,n){this.add(this.getMaker(t,n))},t.prototype.getLine=function(t,n){var e=new AMap.Polyline({path:t.path,extData:t.extData,isOutline:t.isOutline,borderWeight:t.borderWeight||1,outlineColor:t.outlineColor||"#fff",strokeColor:t.strokeColor||"#4875c1",strokeWeight:t.strokeWeight||5,strokeOpacity:t.strokeOpacity||.5,strokeStyle:t.strokeStyle,strokeDasharray:t.strokeDasharray,lineJoin:t.lineJoin||"round",lineCap:t.lineCap,draggable:t.draggable,clickable:!!n});return n&&e.on("click",n),e},t.prototype.addLine=function(t,n){this.add(this.getLine(t,n))},t.prototype.getPolygon=function(t,n){var e=new AMap.Polygon({path:t.path,zIndex:t.zIndex,strokeWeight:t.strokeWeight||2,strokeColor:t.strokeColor||"#fff",strokeOpacity:t.strokeOpacity||.5,strokeStyle:t.strokeStyle,extData:t.extData,strokeDasharray:t.strokeDasharray,fillColor:t.fillColor||"#7a72e5",fillOpacity:t.fillOpacity||.5,draggable:t.draggable,clickable:!!n});return n&&e.on("click",n),e},t.prototype.addPolygon=function(t,n){this.add(this.getPolygon(t,n))},t.prototype.getCircle=function(t,n){var e=new AMap.Circle({center:t.center,zIndex:t.zIndex,extData:t.extData,radius:t.radius||500,strokeColor:t.strokeColor||"#fff",strokeWeight:t.strokeWeight||2,strokeOpacity:t.strokeOpacity||.5,strokeStyle:t.strokeStyle,strokeDasharray:t.strokeDasharray,fillColor:t.fillColor||"#f7ae9e",fillOpacity:t.fillOpacity||.5,clickable:!!n});return n&&e.on("click",n),e},t.prototype.addCircle=function(t,n){this.add(this.getCircle(t,n))},t.prototype.getOverLayGroup=function(t){return new AMap.OverlayGroup(t)},t.prototype.onMapFieldChanged=function(t){this._mapView.on("zoomend",t),this._mapView.on("moveend",t)},t.prototype.showInfoWindow=function(t,n,e){var o=new AMap.InfoWindow({isCustom:n||!0,content:t,offset:e});return o.open(this._mapView),o},t.prototype.showAdvancedInfoWindow=function(t){var n=this;return new p.a(function(e){n._mapView.plugin("AMap.AdvancedInfoWindow",function(){var o=new AMap.AdvancedInfoWindow({panel:t.panel||"panel",placeSearch:t.placeSearch||!0,asOrigin:t.asOrigin||!0,asDestination:t.asDestination||!0,content:t.content});o.open(n._mapView),e.next(o),e.complete()})})},t.prototype.convertFrom=function(t,n){var e=this;return new p.a(function(o){if(t.length<=40)AMap.convertFrom(t,n||"gps",function(t,n){"complete"===t&&"ok"===n.info?o.next(n.locations):console.log(n.info)});else{var i=[];e.covert(i,t,0,n,function(){o.next(i),o.complete()})}})},t.prototype.covert=function(t,n,e,o,i){var a=this,r=n.length-e>40?40:n.length-e;if(r>0){var l=n.slice(e,e+r);AMap.convertFrom(l,o||"gps",function(r,c){if("complete"===r&&"ok"===c.info){var u=c.locations.map(function(t){return[t.lat,t.lng]});t.push.apply(t,u),a.covert(t,n,e+l.length,o,i)}else console.log(c.info)})}else i()},t}(),b=o.nb({encapsulation:0,styles:[[".amap-copyright[_ngcontent-%COMP%], .amap-logo[_ngcontent-%COMP%]{display:none!important}"]],data:{}});function m(t){return o.Jb(0,[o.Fb(402653184,1,{panel:0}),(t()(),o.pb(1,0,null,null,0,"div",[["id","amap-container"],["style","width:100%;height:100%;"]],null,null,null,null,null)),(t()(),o.pb(2,0,[[1,0],["panel",1]],null,0,"div",[["id","panel"],["style","max-width:90%;height:300px;position: absolute;right:50px;top:50px; z-index: 100;overflow: scroll;"]],null,null,null,null,null))],null,null)}var v=e("oCWS"),M=function(){function t(t){this.uiService=t,this.curMapType=0,this.path=[]}return t.prototype.ionViewDidEnter=function(){this.aMap.setZoom(13),this.aMap.setCenter([117.13021,36.67404]),this.addLocationWidget(),this.addToolBar(),this.addScale(),this.addMapType(),this.addMaker(),this.addPolygon(),this.addLine(),this.addCircle()},t.prototype.addLocationWidget=function(){var t=this;this.aMap.addLocationPlugin({maximumAge:5e3,zoomToAccuracy:!0}).subscribe(function(n){t.geolocation=n,AMap.event.addListener(n,"complete",function(n){t.uiService.showAlert(JSON.stringify(n))}),AMap.event.addListener(n,"error",function(n){t.uiService.showAlert(JSON.stringify(n))})})},t.prototype.location=function(){this.geolocation&&this.geolocation.getCurrentPosition()},t.prototype.addToolBar=function(){this.aMap.addToolBarPlugin().subscribe(function(t){})},t.prototype.addScale=function(){this.aMap.addScalePlugin().subscribe(function(t){})},t.prototype.addMapType=function(){this.aMap.addMapTypePlugin().subscribe(function(t){})},t.prototype.toggleMapType=function(){0==this.curMapType?(this.aMap.switchToImg(),this.curMapType=1):(this.aMap.switchToVec(),this.curMapType=0)},t.prototype.addMaker=function(){for(var t=this,n=[],e=0;e<10;e++){var o=[117.13021+.01*e*Math.random(),36.67404+.01*e*Math.random()];this.path.push(o);var i={offset:new AMap.Pixel(-18,-36),position:o,extData:"\u6211\u662f\u7b2c"+e+"\u4e2a\u70b9",icon:new AMap.Icon({size:new AMap.Size(36,36),image:"/assets/common_imgs/th_amap_location.png",imageSize:new AMap.Size(36,36)})};n.push(this.aMap.getMaker(i,function(n){t.uiService.showAlert(n.target.getExtData())}))}var a=this.aMap.getOverLayGroup(n);this.aMap.add(a)},t.prototype.addPolygon=function(){this.aMap.addPolygon({path:this.path},function(t){})},t.prototype.addLine=function(){this.aMap.addLine({path:this.path},function(t){})},t.prototype.addCircle=function(){this.aMap.addCircle({center:[117.13021,36.67404]})},t}(),x=o.nb({encapsulation:0,styles:[[".th-icon-location[_ngcontent-%COMP%]{position:absolute;top:10px;left:10px;padding:10px;background:#fff}.th-icon-navigate[_ngcontent-%COMP%]{position:absolute;top:10px;left:50px;padding:10px;background:#fff}"]],data:{}});function k(t){return o.Jb(0,[o.Fb(402653184,1,{aMap:0}),(t()(),o.pb(1,0,null,null,1,"z-toolbar",[["title","\u9ad8\u5fb7\u5730\u56fe"]],null,null,null,r.b,r.a)),o.ob(2,1097728,null,0,l.a,[c.Jb,c.Gb],{title:[0,"title"]},null),(t()(),o.pb(3,0,null,null,7,"ion-content",[],null,null,null,u.N,u.g)),o.ob(4,49152,null,0,c.t,[o.h,o.k],null,null),(t()(),o.pb(5,0,null,0,1,"a-map",[],null,null,null,m,b)),o.ob(6,1097728,[[1,4],["amap",4]],0,w,[],null,null),(t()(),o.pb(7,0,null,0,1,"ion-icon",[["class","th-icon-location"],["name","logo-buffer"]],null,[[null,"click"]],function(t,n,e){var o=!0;return"click"===n&&(o=!1!==t.component.toggleMapType()&&o),o},u.S,u.l)),o.ob(8,49152,null,0,c.B,[o.h,o.k],{name:[0,"name"]},null),(t()(),o.pb(9,0,null,0,1,"ion-icon",[["class","th-icon-navigate"],["name","navigate"]],null,[[null,"click"]],function(t,n,e){var o=!0;return"click"===n&&(o=!1!==t.component.location()&&o),o},u.S,u.l)),o.ob(10,49152,null,0,c.B,[o.h,o.k],{name:[0,"name"]},null)],function(t,n){t(n,2,0,"\u9ad8\u5fb7\u5730\u56fe"),t(n,8,0,"logo-buffer"),t(n,10,0,"navigate")},null)}function C(t){return o.Jb(0,[(t()(),o.pb(0,0,null,null,1,"page-amap",[],null,null,null,k,x)),o.ob(1,49152,null,0,M,[v.a],null,null)],null,null)}var A=o.lb("page-amap",M,C,{},{},[]),S=e("Ip0R"),P=e("gIcY"),T=e("JR/n"),_=e("16DW"),V=function(){return function(){}}(),L=e("ZYCi");e.d(n,"AMapPageModuleNgFactory",function(){return D});var D=o.mb(i,[],function(t){return o.wb([o.xb(512,o.j,o.bb,[[8,[a.a,A]],[3,o.j],o.x]),o.xb(4608,S.l,S.k,[o.u,[2,S.w]]),o.xb(4608,P.r,P.r,[]),o.xb(4608,c.c,c.c,[o.z,o.g]),o.xb(4608,c.Fb,c.Fb,[c.c,o.j,o.q]),o.xb(4608,c.Kb,c.Kb,[c.c,o.j,o.q]),o.xb(1073742336,S.b,S.b,[]),o.xb(1073742336,P.p,P.p,[]),o.xb(1073742336,P.e,P.e,[]),o.xb(1073742336,c.Cb,c.Cb,[]),o.xb(1073742336,T.a,T.a,[]),o.xb(1073742336,_.a,_.a,[]),o.xb(1073742336,V,V,[]),o.xb(1073742336,L.n,L.n,[[2,L.t],[2,L.m]]),o.xb(1073742336,i,i,[]),o.xb(1024,L.k,function(){return[[{path:"",component:M}]]},[])])})},oCWS:function(t,n,e){"use strict";e.d(n,"a",function(){return u});var o=e("ZZ/e"),i=e("729P"),a=e("zJIM"),r=e("CcnG"),l=function(t,n,e,o){return new(e||(e=Promise))(function(i,a){function r(t){try{c(o.next(t))}catch(n){a(n)}}function l(t){try{c(o.throw(t))}catch(n){a(n)}}function c(t){t.done?i(t.value):new e(function(n){n(t.value)}).then(r,l)}c((o=o.apply(t,n||[])).next())})},c=function(t,n){var e,o,i,a,r={label:0,sent:function(){if(1&i[0])throw i[1];return i[1]},trys:[],ops:[]};return a={next:l(0),throw:l(1),return:l(2)},"function"==typeof Symbol&&(a[Symbol.iterator]=function(){return this}),a;function l(a){return function(l){return function(a){if(e)throw new TypeError("Generator is already executing.");for(;r;)try{if(e=1,o&&(i=2&a[0]?o.return:a[0]?o.throw||((i=o.return)&&i.call(o),0):o.next)&&!(i=i.call(o,a[1])).done)return i;switch(o=0,i&&(a=[2&a[0],i.value]),a[0]){case 0:case 1:i=a;break;case 4:return r.label++,{value:a[1],done:!1};case 5:r.label++,o=a[1],a=[0];continue;case 7:a=r.ops.pop(),r.trys.pop();continue;default:if(!(i=(i=r.trys).length>0&&i[i.length-1])&&(6===a[0]||2===a[0])){r=0;continue}if(3===a[0]&&(!i||a[1]>i[0]&&a[1]<i[3])){r.label=a[1];break}if(6===a[0]&&r.label<i[1]){r.label=i[1],i=a;break}if(i&&r.label<i[2]){r.label=i[2],r.ops.push(a);break}i[2]&&r.ops.pop(),r.trys.pop();continue}a=n.call(t,r)}catch(l){a=[6,l],o=0}finally{e=i=0}if(5&a[0])throw a[1];return{value:a[0]?a[1]:void 0,done:!0}}([a,l])}}},u=function(){function t(t,n,e,o){this.loadingCtrl=t,this.toastCtrl=n,this.actionSheetCtrl=e,this.alertCtrl=o}return t.prototype.showLoading=function(t){return l(this,void 0,void 0,function(){var n;return c(this,function(e){switch(e.label){case 0:return[4,this.loadingCtrl.create({message:t,spinner:"circles"})];case 1:return[4,(n=e.sent()).present()];case 2:return e.sent(),[2,n]}})})},t.prototype.showToast=function(t,n,e){return l(this,void 0,void 0,function(){return c(this,function(o){switch(o.label){case 0:return a.a.isTelchina()?(i.a.toast(t,n),[3,4]):[3,1];case 1:return[4,this.toastCtrl.create({message:t,duration:n||2e3,position:e||"bottom"})];case 2:return[4,o.sent().present()];case 3:o.sent(),o.label=4;case 4:return[2]}})})},t.prototype.showActionSheet=function(t,n,e){return l(this,void 0,void 0,function(){var o;return c(this,function(i){switch(i.label){case 0:return[4,this.actionSheetCtrl.create({header:e,buttons:t.map(function(t,e){return{text:t,cssClass:"z-actionsheet-button",handler:function(){if(n)return n(e,t)}}})})];case 1:return[4,(o=i.sent()).present()];case 2:return i.sent(),[2,o]}})})},t.prototype.showAlert=function(t,n,e,o){return l(this,void 0,void 0,function(){var i;return c(this,function(a){switch(a.label){case 0:return[4,this.alertCtrl.create({header:t,message:n,buttons:[{text:o||"\u786e\u5b9a",handler:function(){if(e)return e()}}]})];case 1:return[4,(i=a.sent()).present()];case 2:return a.sent(),[2,i]}})})},t.prototype.showConfirm=function(t,n,e,o,i,a){return l(this,void 0,void 0,function(){var r;return c(this,function(l){switch(l.label){case 0:return[4,this.alertCtrl.create({header:t,message:n,cssClass:"th-app-alert",buttons:[{text:a||"\u53d6\u6d88",role:"cancel",handler:function(){if(o)return o()}},{text:i||"\u786e\u5b9a",handler:function(){if(e)return e()}}]})];case 1:return[4,(r=l.sent()).present()];case 2:return l.sent(),[2,r]}})})},t.prototype.showCheckboxDialog=function(t,n,e,o,i,a,r){return l(this,void 0,void 0,function(){var l,u,p;return c(this,function(c){switch(c.label){case 0:for(l=[],u=0;u<n.length;u++)l.push({label:n[u],type:"checkbox",checked:!!e&&e.indexOf(u)>=0});return[4,this.alertCtrl.create({header:t,inputs:l,buttons:[{text:r||"\u53d6\u6d88",role:"cancel",handler:function(){if(i)return i()}},{text:a||"\u786e\u5b9a",handler:function(){if(o){var t=l.filter(function(t){return t.checked}).map(function(t,n){return{index:n,label:t.label}});return o(t)}}}]})];case 1:return[4,(p=c.sent()).present()];case 2:return c.sent(),[2,p]}})})},t.prototype.showRadioBoxDialog=function(t,n,e,o,i,a,r){return l(this,void 0,void 0,function(){var l,u,p;return c(this,function(c){switch(c.label){case 0:for(l=[],u=0;u<n.length;u++)l.push({label:n[u],type:"radio",checked:e>=0&&e===u});return[4,this.alertCtrl.create({header:t,inputs:l,buttons:[{text:r||"\u53d6\u6d88",role:"cancel",handler:function(){if(i)return i()}},{text:a||"\u786e\u5b9a",handler:function(){if(o)for(var t=0;t<l.length;t++){var n=l[t];if(n.checked)return o(t,n.label)}}}]})];case 1:return[4,(p=c.sent()).present()];case 2:return c.sent(),[2,p]}})})},t.prototype.showTexDialog=function(t,n,e,o,i){return l(this,void 0,void 0,function(){return c(this,function(a){return[2,this.showInputDialog(t,[{name:"remark",type:"text"}],n,e,o,i)]})})},t.prototype.showInputDialog=function(t,n,e,o,i,a){return l(this,void 0,void 0,function(){var r;return c(this,function(l){switch(l.label){case 0:return[4,this.alertCtrl.create({header:t,inputs:n,buttons:[{text:a||"\u53d6\u6d88",role:"cancel",handler:function(){if(o)return o()}},{text:i||"\u786e\u5b9a",handler:function(){if(e)return e(n.map(function(t){return t.value}))}}]})];case 1:return[4,(r=l.sent()).present()];case 2:return l.sent(),[2,r]}})})},t.ngInjectableDef=r.S({factory:function(){return new t(r.W(o.Eb),r.W(o.Nb),r.W(o.a),r.W(o.b))},token:t,providedIn:"root"}),t}()}}]);