(function(e){function t(t){for(var o,a,c=t[0],u=t[1],l=t[2],p=0,d=[];p<c.length;p++)a=c[p],Object.prototype.hasOwnProperty.call(r,a)&&r[a]&&d.push(r[a][0]),r[a]=0;for(o in u)Object.prototype.hasOwnProperty.call(u,o)&&(e[o]=u[o]);s&&s(t);while(d.length)d.shift()();return i.push.apply(i,l||[]),n()}function n(){for(var e,t=0;t<i.length;t++){for(var n=i[t],o=!0,c=1;c<n.length;c++){var u=n[c];0!==r[u]&&(o=!1)}o&&(i.splice(t--,1),e=a(a.s=n[0]))}return e}var o={},r={app:0},i=[];function a(t){if(o[t])return o[t].exports;var n=o[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,a),n.l=!0,n.exports}a.m=e,a.c=o,a.d=function(e,t,n){a.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},a.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},a.t=function(e,t){if(1&t&&(e=a(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(a.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var o in e)a.d(n,o,function(t){return e[t]}.bind(null,o));return n},a.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return a.d(t,"a",t),t},a.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},a.p="/";var c=window["webpackJsonp"]=window["webpackJsonp"]||[],u=c.push.bind(c);c.push=t,c=c.slice();for(var l=0;l<c.length;l++)t(c[l]);var s=u;i.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"034f":function(e,t,n){"use strict";n("85ec")},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var o=n("2b0e"),r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("login-join"),n("request-post"),n("div",{staticStyle:{display:"flex","flex-direction":"row","justify-content":"space-evenly"}},[n("outbox"),n("inbox"),n("archive")],1)],1)},i=[],a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("input",{directives:[{name:"model",rawName:"v-model",value:e.id,expression:"id"}],attrs:{placeholder:"id"},domProps:{value:e.id},on:{input:function(t){t.target.composing||(e.id=t.target.value)}}}),n("input",{directives:[{name:"model",rawName:"v-model",value:e.pw,expression:"pw"}],attrs:{placeholder:"pw"},domProps:{value:e.pw},on:{input:function(t){t.target.composing||(e.pw=t.target.value)}}}),n("button",{on:{click:e.login}},[e._v("Login")]),n("br"),n("br"),n("input",{directives:[{name:"model",rawName:"v-model",value:e.idj,expression:"idj"}],attrs:{placeholder:"idjoin"},domProps:{value:e.idj},on:{input:function(t){t.target.composing||(e.idj=t.target.value)}}}),n("input",{directives:[{name:"model",rawName:"v-model",value:e.pwj,expression:"pwj"}],attrs:{placeholder:"pwjoin"},domProps:{value:e.pwj},on:{input:function(t){t.target.composing||(e.pwj=t.target.value)}}}),n("button",{on:{click:e.join}},[e._v("Join")]),n("br"),n("br"),n("button",{on:{click:e.logout}},[e._v("Logout")]),n("br"),n("br")])},c=[],u=n("bc3a"),l=n.n(u),s={data:function(){return{id:"",pw:"",idj:"",pwj:""}},methods:{login:function(){var e={id:this.id,password:this.pw};console.log("login request"),l.a.post("/auth/login",e).then((function(){return console.log("eeeeeee")})).catch((function(e){return console.log(e)}))},join:function(){var e={id:this.idj,password:this.pwj};console.log("join request"),l.a.post("/auth/join",e).then((function(){return console.log("eeeeeee")})).catch((function(e){return console.log(e)}))},logout:function(){l.a.post("/api/logout").then((function(){return console.log("eeeeeee")})).catch((function(e){return console.log(e)}))}}},p=s,d=n("2877"),v=Object(d["a"])(p,a,c,!1,null,null,null),f=v.exports,m=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("h2",[e._v("OUTBOX")]),e._l(e.outbox,(function(t){return n("div",{key:t.title},[n("p",[e._v(e._s(t.title))])])}))],2)},h=[],g={created:function(){this.getOutbox()},data:function(){return{outbox:[]}},methods:{getOutbox:function(){var e=this;l.a.get("/api/doc/outbox").then((function(t){console.log(t.data.outbox),e.outbox=t.data.outbox}))}}},b=g,_=Object(d["a"])(b,m,h,!1,null,null,null),x=_.exports,y=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("h2",[e._v("INBOX")]),e._l(e.inbox,(function(t){return n("div",{key:t.doc.title},[n("p",[e._v(e._s(t.doc.title))]),n("input",{directives:[{name:"model",rawName:"v-model",value:e.comment,expression:"comment"}],attrs:{placeholder:"Comment"},domProps:{value:e.comment},on:{input:function(t){t.target.composing||(e.comment=t.target.value)}}}),n("button",{on:{click:function(n){return e.approve(t.doc.id)}}},[e._v("Approve")]),n("button",{on:{click:function(n){return e.deny(t.doc.id)}}},[e._v("Deny")])])}))],2)},w=[],k={created:function(){this.getInbox()},data:function(){return{inbox:[],comment:""}},methods:{getInbox:function(){var e=this;l.a.get("/api/doc/inbox").then((function(t){console.log(t.data.inbox),e.inbox=t.data.inbox}))},approve:function(e){l.a.post("/api/doc/sign",{docId:e,decision:"APPROVED",comment:0==this.comment.length?null:this.comment}).then((function(e){return console.log(e)})).catch((function(e){return console.error(e)}))},deny:function(e){l.a.post("/api/doc/sign",{docId:e,decision:"DENIED",comment:0==this.comment.length?null:this.comment}).then((function(e){return console.log(e)})).catch((function(e){return console.error(e)}))}}},j=k,O=Object(d["a"])(j,y,w,!1,null,null,null),P=O.exports,U=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("h2",[e._v("ARCHIVE")]),e._l(e.archive,(function(t){return n("div",{key:t.title},[n("p",[e._v(e._s(t.title))])])}))],2)},A=[],N={created:function(){this.getArchive()},data:function(){return{archive:[]}},methods:{getArchive:function(){var e=this;l.a.get("/api/doc/archive").then((function(t){console.log(t.data.outbox),e.archive=t.data.outbox}))}}},C=N,E=Object(d["a"])(C,U,A,!1,null,null,null),I=E.exports,S=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("h2",[e._v("결재 문서 생성")]),n("div",{staticStyle:{display:"flex","flex-direction":"row","justify-content":"space-evenly"}},[n("div",[n("h2",[e._v("문서 내용")]),n("input",{directives:[{name:"model",rawName:"v-model",value:e.title,expression:"title"}],attrs:{placeholder:"제목"},domProps:{value:e.title},on:{input:function(t){t.target.composing||(e.title=t.target.value)}}}),n("br"),n("textarea",{directives:[{name:"model",rawName:"v-model",value:e.content,expression:"content"}],attrs:{placeholder:"내용"},domProps:{value:e.content},on:{input:function(t){t.target.composing||(e.content=t.target.value)}}})]),n("div",[n("h2",[e._v("문서 분류")]),e._l(e.cats,(function(t){return n("div",{key:t.name},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.pkCat,expression:"pkCat"}],attrs:{type:"radio",id:t.id},domProps:{value:t.id,checked:e._q(e.pkCat,t.id)},on:{change:function(n){e.pkCat=t.id}}}),n("label",{attrs:{for:t}},[e._v(e._s(t.name))])])}))],2),n("div",[n("h2",[e._v("결재자")]),e._l(e.users,(function(t){return n("div",{key:t.name},[n("input",{directives:[{name:"model",rawName:"v-model",value:e.pkUsers,expression:"pkUsers"}],attrs:{type:"checkbox",id:t.name},domProps:{value:t.name,checked:Array.isArray(e.pkUsers)?e._i(e.pkUsers,t.name)>-1:e.pkUsers},on:{change:function(n){var o=e.pkUsers,r=n.target,i=!!r.checked;if(Array.isArray(o)){var a=t.name,c=e._i(o,a);r.checked?c<0&&(e.pkUsers=o.concat([a])):c>-1&&(e.pkUsers=o.slice(0,c).concat(o.slice(c+1)))}else e.pkUsers=i}}}),n("label",{attrs:{for:t.name}},[e._v(e._s(t.name))]),n("br")])})),n("span",[e._v("체크한 이름 (순서대로): "+e._s(e.pkUsers))])],2)]),n("button",{on:{click:e.save}},[e._v("저장")])])},$=[],q={created:function(){this.getCat(),this.getUsers()},data:function(){return{cats:[],users:[],pkCat:-1,pkUsers:[],title:"",content:""}},methods:{getCat:function(){var e=this;l.a.get("/api/category/all").then((function(t){e.cats=t.data}))},getUsers:function(){var e=this;l.a.get("/api/user/page").then((function(t){e.users=t.data.users}))},save:function(){var e={title:this.title,category:this.pkCat,content:this.content,users:this.pkUsers};console.log(e),l.a.post("/api/doc",e).then((function(e){console.log(e)}))}}},D=q,J=Object(d["a"])(D,S,$,!1,null,null,null),M=J.exports,T={name:"App",components:{LoginJoin:f,RequestPost:M,Outbox:x,Inbox:P,Archive:I}},L=T,R=(n("034f"),Object(d["a"])(L,r,i,!1,null,null,null)),B=R.exports;o["a"].config.productionTip=!1,new o["a"]({render:function(e){return e(B)}}).$mount("#app")},"85ec":function(e,t,n){}});
//# sourceMappingURL=app.2fd12910.js.map