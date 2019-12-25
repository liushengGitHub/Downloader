var jsVersion = 180925, KgUser = KgUser || {
    $: function () {
        for (var e = [], r = 0, t = arguments.length; r < t; r++) {
            var o = arguments[r];
            if ("string" == typeof o && (o = document.getElementById(o)), 1 == t) return o;
            e.push(o)
        }
        return e
    },
    $T: function (e, r) {
        var t = (this.$(r) || document).getElementsByTagName(e || "*");
        return this.$A(t)
    },
    $A: function (e) {
        for (var r = [], t = 0, o = e.length; t < o; t++) r.push(e[t]);
        return r
    },
    $C: function (e, r, t) {
        var o = [], i = 0;
        if (document.getElementsByClassName) {
            var s = this.$(r || document).getElementsByClassName(e);
            if (s = this.$A(s), t && "*" !== t) for (var n = s.length; i < n; i++) s[i].tagName.toLowerCase() === t.toLowerCase() && o.push(s[i]); else o = s
        } else for (n = (s = this.$T(t, r)).length; i < n; i++) new RegExp("\\b" + e + "\\b", "g").test(s[i].className) && o.push(s[i]);
        return o
    },
    addJsVersion: function (e) {
        return -1 == e.indexOf("?") ? e + "?kguser_jv=" + jsVersion : e + "&kguser_jv=" + jsVersion
    },
    isAckHost: function (e, r) {
        var t = e.split("/");
        return t.length <= 2 || t[2] != r
    },
    loadScriptN: function (e, r, t, o) {
        var i = !1, s = null, n = 3e3, a = r || "";
        if (r && "object" == typeof r) {
            KgUser.IsEmpty(r.mid) && (r.mid = KgUser.Cookie.read(KgUser.KgMid.name));
            var g = "";
            for (var c in r) "error" !== c && (g += c + "=" + r[c] + "&");
            a = g.substr(0, g.length - 1), KgUser.IsEmpty(r.timeout) || (n = r.timeout)
        }
        var d = document.createElement("script");
        d.type = "text/javascript", null != a && "" != a && (e = e + "?" + (a || "")), e = KgUser.addJsVersion(e);
        var l = KgUser.KgErrType.busislave;
        -1 < e.indexOf("KgAck") && (l = KgUser.KgErrType.ackjs), d.src = e, d.onload = d.onreadystatechange = function () {
            this.readyState && "complete" != this.readyState && "loaded" != this.readyState || (d.onreadystatechange = d.onload = null, d = null, clearTimeout(s), t && t())
        }, d.onerror = function () {
            i || (clearTimeout(s), r.error && r.error(l))
        }, o && (d.async = "async"), s = setTimeout(function () {
            i = !0, r.error && r.error(l)
        }, n), document.getElementsByTagName("head")[0].appendChild(d)
    },
    loadScript: function (e, r, t) {
        var o = !1, i = null, s = r, n = e, a = r || "";
        if (r && "object" == typeof r) {
            KgUser.IsEmpty(r.mid) && (r.mid = KgUser.Cookie.read(KgUser.KgMid.name));
            var g = "";
            for (var c in r) "error" !== c && (g += c + "=" + r[c] + "&");
            a = g.substr(0, g.length - 1), KgUser.IsEmpty(r.timeout) && (s.timeout = 3500)
        }
        var d = document.createElement("script");
        d.type = "text/javascript", null != a && "" != a && (e = e + "?" + (a || "")), e = KgUser.addJsVersion(e), KgUser.KgUrl.verify = "https://verifyservice.kugou.com/";
        var l = e.split("/");
        if (l.length <= 2) r.error && r.error(KgUser.KgErrType.param); else {
            var u = l[2], p = "login-user.kugou.com" == u || "reg-user.kugou.com" == u, m = KgUser.KgErrType.busislave;
            p ? m = KgUser.KgErrType.busimaster : -1 < e.indexOf("KgAck") && (m = KgUser.KgErrType.ackjs), d.src = e;
            var K = !1;
            d.onload = d.onreadystatechange = function () {
                this.readyState && "complete" != this.readyState && "loaded" != this.readyState || (d.onreadystatechange = d.onload = null, K = !(d = null), clearTimeout(i), t && t())
            }, d.onerror = function () {
                p ? o || (clearTimeout(i), KgUser.loginDRRetry(n, u, s, t)) : r.error && r.error(m)
            }, document.getElementsByTagName("head")[0].appendChild(d), p && (i = setTimeout(function () {
                K || (o = !0, KgUser.loginDRRetry(n, u, s, t))
            }, 3500))
        }
    },
    KgAckId: {
        login: 10015,
        reg: 10016,
        verify: 10017,
        url: "http://serveraddr.serviceweb.kugou.com/KgAck.js",
        url_https: "https://serveraddrweb.kugou.com/KgAckV2.js"
    },
    ackCallBack: function (e) {
        if (1 == e.errorCode) {
            var r = KgUser.KgAckId.global_param, t = e.data, o = r.loginHost, i = t["server_" + KgUser.KgAckId.login];
            if (!KgUser.IsEmpty(i)) {
                i = i.list[0];
                KgUser.isAckHost(KgUser.KgUrl.login, o) || (KgUser.KgUrl.verify = "http://" + i, KgUser.KgUrl.login = KgUser.KgUrl.login.replace(o, i), KgUser.KgUrl.loginHTTP = KgUser.KgUrl.loginHTTP.replace(o, i)), KgUser.loadScriptN(r.url.replace(o, i), r.args, r.callback)
            }
        }
    },
    ackCallBackUpdateCookie: function (e) {
        setTimeout(function () {
            var e = "ACK_SERVER_" + KgUser.KgAckId.login, r = KgUser.Cookie.read(e);
            KgUser.IsEmpty(r) || KgUser.Cookie.write(e, r, 3600, "/")
        }, 500)
    },
    KgErrType: {busimaster: 1, busislave: 2, ackjs: 3, ackconf: 4},
    loadAckJs: function (r) {
        for (var e = [KgUser.KgAckId.login, KgUser.KgAckId.reg, KgUser.KgAckId.verify], t = new Array, o = "ACK_SERVER_", i = 0; i < e.length; i++) {
            var s = o + e[i], n = KgUser.Cookie.read(s);
            KgUser.IsEmpty(n) && t.push(e[i])
        }
        var a = 88;
        if (r && r.appid && 0 < parseInt(r.appid) && (a = r.appid), window.getAckConfCallback = function () {
            if ("undefined" != typeof KgAck) {
                var e = new Object;
                e.appid = a, e.clientver = 10, e.clienttime = (new Date).getTime(), e.plats = t, void 0 !== r ? (KgAck.getConfig(e, "KgUser.ackCallBack"), r.error && setTimeout(function () {
                    KgUser.IsEmpty(KgUser.Cookie.read(o + KgUser.KgAckId.login)) && r.error(KgUser.KgErrType.ackconf)
                }, timeout)) : KgAck.getConfig(e, "KgUser.ackCallBackUpdateCookie")
            }
        }, 0 < t.length) if ("undefined" == typeof KgAck) {
            var g = KgUser.KgAckId.url;
            "https:" == document.location.protocol && (g = KgUser.KgAckId.url_https);
            var c = new Object;
            c.timeout = 3e3, r && r.timeout && (c.timeout = r.timeout), r && r.error && (c.error = r.error), KgUser.loadScriptN(g, c, getAckConfCallback, !0)
        } else getAckConfCallback()
    },
    ackRetry: function (e, r, t, o) {
        var i = "login-user.kugou.com", s = KgUser.Cookie.read("ACK_SERVER_" + KgUser.KgAckId.login);
        if (!KgUser.IsEmpty(s)) {
            s = KgUser.JSON.parse(s).list[0];
            return KgUser.isAckHost(KgUser.KgUrl.login, i) || (KgUser.KgUrl.verify = "http://" + s, KgUser.KgUrl.login = KgUser.KgUrl.login.replace(i, s), KgUser.KgUrl.loginHTTP = KgUser.KgUrl.loginHTTP.replace(i, s)), void KgUser.loadScriptN(e.replace(r, s), t, o)
        }
        var n = new Object;
        n.loginHost = i, n.url = e, n.args = t, n.callback = o, KgUser.KgAckId.global_param = n, this.loadAckJs(t)
    },
    KgMid: {name: "kg_mid", days: 1e4},
    JSON: function () {
        function f(e) {
            return e < 10 ? "0" + e : e
        }

        Date.prototype.toJSON = function () {
            return this.getUTCFullYear() + "-" + f(this.getUTCMonth() + 1) + "-" + f(this.getUTCDate()) + "T" + f(this.getUTCHours()) + ":" + f(this.getUTCMinutes()) + ":" + f(this.getUTCSeconds()) + "Z"
        };
        var m = {"\b": "\\b", "\t": "\\t", "\n": "\\n", "\f": "\\f", "\r": "\\r", '"': '\\"', "\\": "\\\\"};

        function stringify(e, r) {
            var t, o, i, s, n, a = /["\\\x00-\x1f\x7f-\x9f]/g;
            switch (typeof e) {
                case"string":
                    return a.test(e) ? '"' + e.replace(a, function (e) {
                        var r = m[e];
                        return r || (r = e.charCodeAt(), "\\u00" + Math.floor(r / 16).toString(16) + (r % 16).toString(16))
                    }) + '"' : '"' + e + '"';
                case"number":
                    return isFinite(e) ? String(e) : "null";
                case"boolean":
                case"null":
                    return String(e);
                case"object":
                    if (!e) return "null";
                    if ("function" == typeof e.toJSON) return stringify(e.toJSON());
                    if (t = [], "number" == typeof e.length && !e.propertyIsEnumerable("length")) {
                        for (s = e.length, o = 0; o < s; o += 1) t.push(stringify(e[o], r) || "null");
                        return "[" + t.join(",") + "]"
                    }
                    if (r) for (s = r.length, o = 0; o < s; o += 1) "string" == typeof (i = r[o]) && (n = stringify(e[i], r)) && t.push(stringify(i) + ":" + n); else for (i in e) "string" == typeof i && (n = stringify(e[i], r)) && t.push(stringify(i) + ":" + n);
                    return "{" + t.join(",") + "}"
            }
        }

        return {
            stringify: stringify, parse: function (text, filter) {
                var j;

                function walk(e, r) {
                    var t, o;
                    if (r && "object" == typeof r) for (t in r) Object.prototype.hasOwnProperty.apply(r, [t]) && (void 0 !== (o = walk(t, r[t])) ? r[t] = o : delete r[t]);
                    return filter(e, r)
                }

                if (/^[\],:{}\s]*$/.test(text.replace(/\\["\\\/bfnrtu]/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) return j = eval("(" + text + ")"), "function" == typeof filter ? walk("", j) : j;
                throw new SyntaxError("parseJSON")
            }
        }
    }(),
    Cookie: {
        write: function (e, r, t, o, i, s) {
            var n = "";
            if (t) {
                var a = new Date;
                a.setTime(a.getTime() + 1e3 * t), n = ";expires=" + a.toGMTString()
            }
            r = encodeURIComponent(r), o = o ? "; path=" + o : ";path=/", i = i ? "; domain=" + i : "", s = s ? "; secure" : "", document.cookie = [e, "=", r, n, o, i, s].join("")
        }, writeUnencodeVaule: function (e, r, t, o, i, s) {
            var n = "";
            if (t) {
                var a = new Date;
                a.setTime(a.getTime() + 1e3 * t), n = ";expires=" + a.toGMTString()
            }
            r = r || "", o = o ? "; path=" + o : ";path=/", i = i ? "; domain=" + i : "", s = s ? "; secure" : "", document.cookie = [e, "=", r, n, o, i, s].join("")
        }, setDay: function (e, r, t, o, i, s) {
            this.write(e, r, 24 * t * 60 * 60, o, i, s)
        }, setHour: function (e, r, t, o, i, s) {
            this.write(e, r, 60 * t * 60, o, i, s)
        }, setMin: function (e, r, t, o, i, s) {
            this.write(e, r, 60 * t, o, i, s)
        }, setSec: function (e, r, t, o, i, s) {
            this.write(e, r, t, o, i, s)
        }, read: function (e, r) {
            var t = "", o = e + "=";
            if (0 < document.cookie.length && (offset = document.cookie.indexOf(o), -1 != offset && (offset += o.length, end = document.cookie.indexOf(";", offset), -1 == end && (end = document.cookie.length), t = document.cookie.substring(offset, end), void 0 !== r && null != r && "" != r))) for (var i = t.toString().split("&"), s = 0; s < i.length; s++) {
                var n = i[s];
                if (n.substring(0, r.length + 1) == r + "=") {
                    t = n.substring(r.length + 1).replace(/%/g, "\\");
                    break
                }
                t = ""
            }
            return decodeURIComponent(t)
        }, remove: function (e) {
            this.write(e, "", -90)
        }
    },
    KgUrl: {
        login: "https://login-user.kugou.com/",
        loginHTTP: "http://login-user.kugou.com/",
        verify: "https://verifyservice.kugou.com/",
        third: "https://openplat-user.kugou.com/",
        cross: "https://loginservice.kugou.com/",
        reg: "https://reg-user.kugou.com/",
        user: "https://userinfoservice.kugou.com/",
        qrcodeH5: "https://h5.kugou.com/apps/loginQRCode/html/index.html"
    },
    LoadCss: function (e, r) {
        var t = document.createElement("style"), o = document.head || document.getElementsByTagName("head")[0];
        if (t.type = "text/css", t.id = r, t.styleSheet) {
            var i = function () {
                try {
                    t.styleSheet.cssText = e
                } catch (e) {
                }
            };
            t.styleSheet.disabled ? setTimeout(i, 10) : i()
        } else {
            var s = document.createTextNode(e);
            t.appendChild(s)
        }
        o.appendChild(t)
    },
    Login: function (t, o) {
        var i, s, n, a = "", g = "", c = 1, d = !1, l = "", u = "", p = "", m = "";
        if (KgUser.IsEmpty(t.appid) || KgUser.IsEmpty(KgUser.Trim(t.username)) || KgUser.IsEmpty(KgUser.Trim(t.pwd)) || KgUser.IsEmpty(o)) return KgUser.GetMsg(o, {
            errorCode: "20010",
            errorMsg: "必填参数不能为空"
        }), !1;
        i = t.appid, s = KgUser.Trim(t.username), n = KgUser.Trim(t.pwd), KgUser.IsEmpty(KgUser.Trim(t.code)) || (a = KgUser.Trim(t.code)), KgUser.IsEmpty(KgUser.Trim(t.ticket)) || (g = KgUser.Trim(t.ticket)), KgUser.IsEmpty(t.expire_day) || (c = t.expire_day), KgUser.IsEmpty(t.autologin) || (d = t.autologin), KgUser.IsEmpty(t.redirect_uri) || (l = t.redirect_uri), KgUser.IsEmpty(t.state) || (u = t.state), KgUser.IsEmpty(KgUser.Trim(t.mobile)) || (p = KgUser.Trim(t.mobile)), KgUser.IsEmpty(KgUser.Trim(t.mobile_code)) || (m = KgUser.Trim(t.mobile_code)), d && (c = 30);
        var K = KgUser.getPlat(i), U = "";
        KgUser.getDfidAsyn(i, function (e) {
            U = e;
            var r = {
                appid: i,
                username: encodeURIComponent(s),
                pwd: KgUser.Md5(n),
                code: a,
                ticket: g,
                clienttime: parseInt((new Date).getTime() / 1e3),
                expire_day: c,
                autologin: d,
                redirect_uri: encodeURIComponent(l),
                state: encodeURIComponent(u),
                callback: o,
                error: t.error,
                login_ver: 1,
                mobile: p,
                mobile_code: m,
                plat: K,
                dfid: U
            };
            KgUser.loadScript(KgUser.KgUrl.login + "v1/login/", r)
        })
    },
    LoginByVerifycode: function (e, o) {
        var i, s, n = "", a = 1, g = 1;
        if (KgUser.IsEmpty(e.appid) || KgUser.IsEmpty(KgUser.Trim(e.mobile)) || KgUser.IsEmpty(KgUser.Trim(e.code)) || KgUser.IsEmpty(o)) return KgUser.GetMsg(o, {
            errorCode: "20010",
            errorMsg: "必填参数不能为空"
        }), !1;
        i = e.appid, s = KgUser.Trim(e.mobile), KgUser.IsEmpty(KgUser.Trim(e.code)) || (n = KgUser.Trim(e.code)), KgUser.IsEmpty(e.expire_day) || (a = e.expire_day), KgUser.IsEmpty(e.force_login) && 0 != e.force_login || (g = e.force_login);
        var c = KgUser.getPlat(i), d = "";
        KgUser.getDfidAsyn(i, function (e) {
            d = e;
            var r = {
                appid: i,
                mobile: encodeURIComponent(s),
                code: n,
                clienttime: parseInt((new Date).getTime() / 1e3),
                expire_day: a,
                callback: "KgUser.LoginByCodeVerifyCallback",
                login_ver: 1,
                plat: c,
                dfid: d,
                force_login: g
            }, t = KgUser.getKgMid();
            KgUser.LoginByCodeVerifyCallbackParam.appid = i, KgUser.LoginByCodeVerifyCallbackParam.mid = t, KgUser.LoginByCodeVerifyCallbackParam.callbackName = o, KgUser.loadScript(KgUser.KgUrl.login + "v1/loginbyverifycode/", r)
        })
    },
    LoginByVerifycodeV2: function (e, o) {
        var i, s, n = "", a = 1, g = 1, c = KgUser.getKgMid(), d = "", l = 1;
        if (KgUser.IsEmpty(e.appid) || KgUser.IsEmpty(KgUser.Trim(e.mobile)) || KgUser.IsEmpty(KgUser.Trim(e.code)) || KgUser.IsEmpty(o)) return KgUser.GetMsg(o, {
            errorCode: "20010",
            errorMsg: "必填参数不能为空"
        }), !1;
        i = e.appid, s = KgUser.Trim(e.mobile), KgUser.IsEmpty(KgUser.Trim(e.code)) || (n = KgUser.Trim(e.code)), KgUser.IsEmpty(e.expire_day) || (a = e.expire_day), KgUser.IsEmpty(e.support_multi) || (g = e.support_multi), KgUser.IsEmpty(e.userid) || (d = e.userid), KgUser.IsEmpty(e.force_login) && 0 != e.force_login || (l = e.force_login);
        var u = "", p = KgUser.getPlat(i);
        KgUser.getDfidAsyn(i, function (e) {
            u = e;
            var r = {appid: i, clientver: 10, clienttime: parseInt((new Date).getTime() / 1e3), mid: c, dfid: u}, t = {
                plat: p,
                mobile: encodeURIComponent(s),
                code: n,
                expire_day: a,
                support_multi: g,
                userid: d,
                force_login: l
            };
            window.getInterFacePublic ? getInterFacePublic(r, t, function (e) {
                var r = KgUser.objectToGetParams(e);
                KgUser.sentPostRequest(KgUser.KgUrl.login + "v2/loginbyverifycode/?" + r, t, function (e) {
                    KgUser.LoginByCodeVerifyCallback(e, i, c, o, a)
                })
            }) : KgUser.loadScript("https://staticssl.kugou.com/common/js/min/inf_public-min.js", null, function () {
                getInterFacePublic(r, t, function (e) {
                    var r = KgUser.objectToGetParams(e);
                    KgUser.sentPostRequest(KgUser.KgUrl.login + "v2/loginbyverifycode/?" + r, t, function (e) {
                        KgUser.LoginByCodeVerifyCallback(e, i, c, o, a)
                    })
                })
            })
        })
    },
    LoginByCodeVerifyCallbackParam: {appid: "", mid: "", callbackName: ""},
    LoginByCodeVerifyCallback: function (e, r, t, o, i) {
        if ("string" == typeof e && (e = JSON.parse(e)), null == r && null == t && null == o && (r = KgUser.LoginByCodeVerifyCallbackParam.appid, t = KgUser.LoginByCodeVerifyCallbackParam.mid, o = KgUser.LoginByCodeVerifyCallbackParam.callbackName), 20028 == e.errorCode) {
            var s = e.eventid;
            1058 == r ? KgUser.antiBrushH5({
                eventid: s,
                userid: 0,
                appid: r,
                mid: t,
                url: window.location.href,
                callback: function (e) {
                    KgUser.GetMsg(o, e)
                }
            }) : KgUser.antiBrush({
                eventid: s,
                userid: 0,
                appid: r,
                mid: t,
                url: window.location.href,
                callback: function (e) {
                    KgUser.GetMsg(o, e)
                }
            })
        } else {
            if (1 == e.status && 0 == e.error_code) try {
                var n = e.data.domain, a = e.data.name, g = e.data.path, c = e.data.value;
                KgUser.Cookie.writeUnencodeVaule(a, c, 86400 * i, g, n)
            } catch (e) {
            }
            KgUser.GetMsg(o, e)
        }
    },
    objectToGetParams: function (e) {
        var r = "";
        for (var t in e) e.hasOwnProperty(t) && (r = "" == r ? r + t + "=" + e[t] : r + "&" + t + "=" + e[t]);
        return r
    },
    VerifyCode: function (e, r) {
        var t, o, s, n = 90, a = 40, g = "LoginCheckCode", c = "", d = 0;
        if (KgUser.IsEmpty(e.appid)) return !1;
        t = e.appid, o = KgUser.Trim(e.codeid), KgUser.IsEmpty(KgUser.Trim(e.width)) || (n = KgUser.Trim(e.width)), KgUser.IsEmpty(KgUser.Trim(e.height)) || (a = KgUser.Trim(e.height)), KgUser.IsEmpty(KgUser.Trim(e.type)) || (g = KgUser.Trim(e.type)), KgUser.IsEmpty(KgUser.Trim(e.inputid)) || (c = KgUser.Trim(e.inputid));
        d = KgUser.IsEmpty(e.codetype) ? 0 : e.codetype;
        if (s = Math.round(100 * Math.random()), "RegCheckCode" == g && void 0 === e.codetype) {
            0 <= s && s <= 100 ? d = 1 : 31 <= s && s <= 40 && (d = 2);
            var l = c.split(",");
            for (i = 0; i < l.length; i++) void 0 !== KgUser.$(l[i]) && (KgUser.$(l[i]).style.display = 0 != d ? "none" : "")
        }
        KgUser.CodeAll.codetype = d, KgUser.CodeAll.appid = t, KgUser.CodeAll.codeid = o;
        var u = "";
        switch (u = (u = 3 == d && "LoginCheckCode" == g ? KgUser.KgUrl.login : "LoginCheckCode" == g || "RegCheckCode" == g || "SmsCheckCode" == g ? KgUser.KgUrl.login : KgUser.KgUrl.verify) + "v1/get_img_code?type=" + g + "&appid=" + t + "&codetype=" + d + "&t=" + (new Date).getTime(), d) {
            case 1:
                setTimeout(function () {
                    KgUser.SudokuCode({url: u, id: e.codeid, appid: t}, r)
                }, 100);
                break;
            case 2:
                setTimeout(function () {
                    KgUser.ThirdCode({id: e.codeid, url: u}, r)
                }, 100);
                break;
            case 3:
                KgUser.SliderVerifyCode({
                    url: u,
                    appid: t,
                    success: e.success,
                    close: e.close,
                    update: e.update,
                    timeout: e.timeout,
                    callback: e.callback,
                    callback_deg: e.callback_deg
                });
                break;
            default:
                setTimeout(function () {
                    KgUser.$(o).style.display = "", KgUser.$(o).innerHTML = '<img id="KgPopupVerifyCode" src="' + u + '" onclick="KgUser.ChangeVerifyCode(this,' + t + ",'" + g + '\')" title="看不清，换一张" alt="看不清，换一张" style="cursor:pointer;width:' + n + "px;height:" + a + 'px;" />'
                }, 100)
        }
    },
    ChangeVerifyCode: function (r, t, o) {
        setTimeout(function () {
            var e = "";
            e = "LoginCheckCode" == o || "RegCheckCode" == o || "SmsCheckCode" == o ? KgUser.KgUrl.login : KgUser.KgUrl.verify, r.src = e + "v1/get_img_code?type=" + o + "&appid=" + t + "&t=" + (new Date).getTime()
        }, 100)
    },
    CheckVerifyCode: function (e) {
        var r, t, o = parseInt((new Date).getTime() / 1e3);
        if (KgUser.IsEmpty(e.appid) || KgUser.IsEmpty(KgUser.Trim(e.username))) return !1;
        r = e.appid, t = KgUser.Trim(e.username);
        var i = {appid: r, username: encodeURIComponent(t), t: o};
        KgUser.loadScript(KgUser.KgUrl.login + "v1/check/", i)
    },
    ThirdLogin: function (e, r) {
        var t = "", o = "", i = 0, s = 0, n = "", a = 0, g = parseInt((new Date).getTime() / 1e3), c = "",
            d = Number(-1 < location.href.indexOf("staticssl"));
        KgUser.IsEmpty(e.appid) || (a = e.appid), KgUser.IsEmpty(KgUser.Trim(e.partner)) || (n = KgUser.Trim(e.partner).toLowerCase()), KgUser.IsEmpty(KgUser.Trim(e.client)) || (c = KgUser.Trim(e.client).toLowerCase());
        var l = "";
        switch (n) {
            case"qzone":
            case"qq":
                t = KgUser.KgUrl.third + "qq/?", o = "TencentLogin", i = 470, s = 340, l = "qq";
                break;
            case"sina":
                t = KgUser.KgUrl.third + "sina/?", o = "SinaLogin", i = 562, s = 380;
                break;
            case"qqweibo":
                t = KgUser.KgUrl.third + "qqweibo/?", o = "QQWeiBoLogin", i = 563, s = 387;
                break;
            case"renren":
                t = KgUser.KgUrl.third + "renren/?", o = "RenrenLogin", i = 563, s = 387;
                break;
            case"weixin":
                t = KgUser.KgUrl.third + "weixin/index.php?ktype=0", o = "WeiXinLogin", i = 563, s = 593, l = "wechat";
                break;
            case"esurfing":
                t = KgUser.KgUrl.third + "esurfing/?", o = "EsurLogin", i = 800, s = 458
        }
        var u = 0;
        KgUser.IsEmpty(KgUser.Trim(e.bind)) || (u = KgUser.Trim(e.bind));
        var p = "";
        if (KgUser.IsEmpty(KgUser.Trim(e.ktype)) || (p = "&ktype=" + e.ktype), 1 == u && "" != l && (t = "https://openplat-user.kugou.com/bind?userid=0&bind=1&partnername=" + l + p), t) {
            t += "&appid=" + a + "&clienttime=" + g + "&callback=" + r + "&domain=" + KgUser.GetDomain() + "&https=" + d;
            var m = KgUser.Cookie.read(KgUser.KgMid.name);
            KgUser.IsEmpty(m) || (t += "&mid=" + m);
            var K = KgUser.getPlat(a);
            KgUser.getDfidAsyn(a, function (e) {
                t += "&dfid=" + e + "&platid=" + K, function () {
                    var e = (window.screen.width - i) / 2, r = (window.screen.height - s) / 2;
                    if ("mackugou" == c || "uwp" == c) {
                        switch (t += "&client=" + c, n) {
                            case"qzone":
                                i = 540, s = 400;
                                break;
                            case"sina":
                                i = 768, s = 400
                        }
                        Supercall && Supercall.setView({
                            type: "child",
                            window: "contentWindow",
                            name: "ThirdLogin",
                            options: {width: i, height: s, url: t, parent: "login", standButton: ["close"]}
                        }, function () {
                        })
                    } else window.open(t, o, "width=" + i + ",height=" + s + ",left=" + e + ",top=" + r + ",menubar=0,scrollbars=0,resizable=0,status=0,titlebar=0,toolbar=0,location=1")
                }()
            })
        }
    },
    SliderVerifyCode: function (r) {
        var t = this, o = r.success, i = r.close, s = r.update, n = r.callback, a = r.callback_deg, g = r.timeout,
            e = r.url, c = !1;
        if (t.tcaptchaJsUrl) d(); else {
            if (t.loadingTcaptchaJsUrl) return;
            t.loadingTcaptchaJsUrl = !0, window.kgSliderVerifyCodeHandler = function (e) {
                t.loadingTcaptchaJsUrl = !1, e && 0 == e.success && 1 == e.isDegrade ? a && a() : e && e.success && e.url ? (KgUser.loadScript(e.url, "", function () {
                    c = !0, t.tcaptchaJsUrl = e.url, t.loadingTcaptchaJsUrl = !1, 1 <= window.navigator.userAgent.indexOf("MSIE") ? setTimeout(function () {
                        d()
                    }, 500) : d()
                }), g && 0 < g && setTimeout(function () {
                    c || n && n(!1)
                }, g)) : (t.loadingTcaptchaJsUrl = !1, void 0 === r.retry && (r.retry = 3), 0 < r.retry && (r.retry--, t.SliderVerifyCode(r)))
            }, this.loadScript(e + "&callback=kgSliderVerifyCodeHandler")
        }

        function d() {
            window.capDestroy();
            var e = {
                showHeader: !0, type: "popup", start: function () {
                }, callback: function (e) {
                    c = !0, function (e) {
                        if (!c) return;
                        if (e.ticket && 0 == e.ret) {
                            var r = e.ticket;
                            o && o(r)
                        } else i && i()
                    }(e)
                }
            };
            window.capRefresh(), s && s(), n && n(!0), window.capInit(document, e)
        }
    },
    CrossDomainLogin: function (callbackName) {
        var appid = 0, userid = 0, token = "", ct = parseInt((new Date).getTime() / 1e3), domain = KgUser.GetDomain();
        KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "a_id")) || (appid = parseInt(KgUser.Cookie.read("KuGoo", "a_id"))), KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "KugooID")) || (userid = parseInt(KgUser.Cookie.read("KuGoo", "KugooID"))), KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "ct")) || (ct = parseInt(KgUser.Cookie.read("KuGoo", "ct"))), KgUser.IsEmpty(KgUser.Trim(KgUser.Cookie.read("KuGoo", "t"))) || (token = KgUser.Trim(KgUser.Cookie.read("KuGoo", "t")));
        var param = {
            a_id: appid,
            userid: userid,
            t: token,
            ct: ct,
            callback: "",
            domain: domain,
            midname: KgUser.KgMid.name,
            days: KgUser.KgMid.days
        }, url = KgUser.KgUrl.login;
        KgUser.loadScript(url + "v1/cross/", param, function () {
            if (!KgUser.IsEmpty(callbackName)) {
                var uinfo = new Object;
                KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "KugooID")) || (uinfo.userid = parseInt(KgUser.Cookie.read("KuGoo", "KugooID"))), KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "UserName")) || (uinfo.username = eval("'" + KgUser.Trim(KgUser.Cookie.read("KuGoo", "UserName")) + "'")), KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "NickName")) || (uinfo.nickname = eval("'" + KgUser.Trim(KgUser.Cookie.read("KuGoo", "NickName")) + "'")), KgUser.IsEmpty(KgUser.Trim(KgUser.Cookie.read("KuGoo", "t"))) || (uinfo.token = KgUser.Trim(KgUser.Cookie.read("KuGoo", "t"))), KgUser.IsEmpty(KgUser.Trim(KgUser.Cookie.read("KuGoo", "Pic"))) || (uinfo.pic = KgUser.Trim(KgUser.Cookie.read("KuGoo", "Pic"))), KgUser.GetMsg(callbackName, uinfo)
            }
        })
    },
    AutoLogin: function (e) {
        var o = 0, i = 0, s = "", n = parseInt((new Date).getTime() / 1e3), a = KgUser.GetDomain();
        KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "a_id")) || (o = parseInt(KgUser.Cookie.read("KuGoo", "a_id"))), KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "KugooID")) || (i = parseInt(KgUser.Cookie.read("KuGoo", "KugooID"))), KgUser.IsEmpty(KgUser.Cookie.read("KuGoo", "ct")) || (n = parseInt(KgUser.Cookie.read("KuGoo", "ct"))), KgUser.IsEmpty(KgUser.Trim(KgUser.Cookie.read("KuGoo", "t"))) || (s = KgUser.Trim(KgUser.Cookie.read("KuGoo", "t")));
        var g = KgUser.getPlat(o);
        KgUser.getDfidAsyn(o, function (e) {
            var r = {a_id: o, userid: i, t: s, ct: n, callback: "", domain: a, plat: g, dfid: e},
                t = KgUser.KgUrl.login;
            KgUser.IsEmpty(s) || KgUser.loadScript(t + "v1/autologin", r)
        })
    },
    LoginOut: function (e) {
        KgUser.Cookie.setDay("KuGoo", "", -1, "", "." + KgUser.GetDomain(), "");
        var r = KgUser.KgUrl.cross + "v2/loginout/?a_id=" + e + "&ct=" + parseInt((new Date).getTime() / 1e3) + "&domain=" + KgUser.GetDomain();
        if (/MSIE (\d+\.\d+);/.test(navigator.userAgent) || /MSIE(\d+\.\d+);/.test(navigator.userAgent)) {
            var t = document.createElement("a");
            t.href = r, document.body.appendChild(t), t.click()
        } else location.href = r
    },
    GetDomain: function () {
        return "" == document.domain.toString() ? "" : document.domain.toString().match(/\w*\.(com.cn|com|net.cn|net|org.cn|org|gov.cn|gov|cn|mobi|me|info|name|biz|cc|tv|asia|hk|网络|公司|中国).*$/g)[0]
    },
    GetMsg: function (e, r) {
        if (Array.prototype.reduce || (Array.prototype.reduce = function (e) {
            if (null === this) throw new TypeError("Array.prototype.reduce called on null or undefined");
            if ("function" != typeof e) throw new TypeError(e + " is not a function");
            var r, t = Object(this), o = t.length >>> 0, i = 0;
            if (2 == arguments.length) r = arguments[1]; else {
                for (; i < o && !(i in t);) i++;
                if (o <= i) throw new TypeError("Reduce of empty array with no initial value");
                r = t[i++]
            }
            for (; i < o; i++) i in t && (r = e(r, t[i], i, t));
            return r
        }), !KgUser.IsEmpty(e)) {
            var t = null;
            try {
                t = e.split(".").reduce(function (e, r) {
                    return e[r]
                }, window)
            } catch (e) {
                t = null
            }
            "function" == typeof t && t(r)
        }
    },
    GetSmsCode: function (e, t) {
        var o, i;
        if (KgUser.IsEmpty(e.appid) || KgUser.IsEmpty(KgUser.Trim(e.mobile))) return KgUser.GetMsg(t, {
            errorCode: "20010",
            errorMsg: "必填参数不能为空"
        }), !1;
        KgUser.IsEmpty(e.type) && (e.type = "reg"), o = e.appid, i = KgUser.Trim(e.mobile), type = KgUser.Trim(e.type), verifycode = e.verifycode, KgUser.IsEmpty(verifycode) && (verifycode = "");
        var s = KgUser.getPlat(o), n = "";
        KgUser.getDfidAsyn(o, function (e) {
            n = e;
            var r = {
                appid: o,
                mobile: encodeURIComponent(i),
                callback: t,
                v: 1,
                verifycode: verifycode,
                ct: parseInt((new Date).getTime() / 1e3),
                type: type,
                plat: s,
                dfid: n
            };
            "reg" != type ? KgUser.loadScript(KgUser.KgUrl.login + "v1/send_sms/", r) : KgUser.loadScript(KgUser.KgUrl.login + "v1/send_sms_for_reg", r)
        })
    },
    RegByUserName: function (e, t) {
        var o, i, s, n, a = 1, g = 1, c = "", d = "", l = "", u = "";
        if (1 == KgUser.CodeAll.codetype && (e.code = KgUser.CodeAll.cval), KgUser.IsEmpty(e.appid) || KgUser.IsEmpty(KgUser.Trim(e.username)) || KgUser.IsEmpty(KgUser.Trim(e.password))) return KgUser.GetMsg(t, {
            errorCode: "20010",
            errorMsg: "必填参数不能为空"
        }), !1;
        if (KgUser.IsEmpty(KgUser.Trim(e.code)) && 2 != KgUser.CodeAll.codetype) return KgUser.GetMsg(t, {
            errorCode: "20010",
            errorMsg: "验证码不能为空"
        }), !1;
        o = e.appid, s = KgUser.Trim(e.username), n = KgUser.Trim(e.password), i = KgUser.Trim(e.code), KgUser.IsEmpty(e.expire_day) || (a = e.expire_day), void 0 !== e.sex && null != e.sex && (g = e.sex), c = KgUser.IsEmpty(KgUser.Trim(e.nickname)) ? s : KgUser.Trim(e.nickname), KgUser.IsEmpty(KgUser.Trim(e.security_email)) || (d = KgUser.Trim(e.security_email)), KgUser.IsEmpty(KgUser.Trim(e.id_card)) || (l = KgUser.Trim(e.id_card)), KgUser.IsEmpty(KgUser.Trim(e.truename)) || (u = KgUser.Trim(e.truename));
        var p = KgUser.getPlat(o), m = "";
        KgUser.getDfidAsyn(o, function (e) {
            m = e;
            var r = {
                regtype: "username",
                appid: o,
                code: encodeURIComponent(i),
                expire_day: a,
                v: 1,
                username: encodeURIComponent(s),
                sex: g,
                password: KgUser.Md5(n),
                nickname: encodeURIComponent(c),
                security_email: encodeURIComponent(d),
                id_card: encodeURIComponent(l),
                truename: encodeURIComponent(u),
                callback: t,
                codetype: KgUser.CodeAll.codetype,
                plat: p,
                dfid: m
            };
            2 == KgUser.CodeAll.codetype && void 0 !== KgUser.$C("geetest_challenge", "", "input")[0] && (r.geetest_challenge = KgUser.$C("geetest_challenge", KgUser.CodeAll.codeid, "input")[0].value, r.geetest_validate = KgUser.$C("geetest_validate", KgUser.CodeAll.codeid, "input")[0].value, r.geetest_seccode = KgUser.$C("geetest_seccode", KgUser.CodeAll.codeid, "input")[0].value), KgUser.loadScript(KgUser.KgUrl.login + "v2/reg", r)
        })
    },
    RegByMobile: function (t, o) {
        window.saveTempInfoObj = KgUser.JSON.parse(KgUser.JSON.stringify(t));
        var i, s, n, a = 1, g = 1, c = "", d = "";
        i = t.appid, n = KgUser.Trim(t.mobile), s = KgUser.Trim(t.code);
        var l = {}, u = void 0 === t.mid ? "" : t.mid, p = void 0 === t.type ? "" : t.type, m = KgUser.getPlat(i),
            K = "";
        KgUser.getDfidAsyn(i, function (e) {
            K = e;
            var r = "tempCallbackName";
            if ("" == p) {
                if (KgUser.IsEmpty(KgUser.Trim(t.password))) return KgUser.GetMsg(o, {
                    errorCode: "20010",
                    errorMsg: "必填参数不能为空"
                }), !1;
                c = KgUser.Trim(t.password), KgUser.IsEmpty(t.expire_day) || (a = t.expire_day), void 0 !== t.sex && null != t.sex && (g = t.sex), d = KgUser.IsEmpty(KgUser.Trim(t.nickname)) ? n : KgUser.Trim(t.nickname), l = {
                    regtype: "mobile",
                    appid: i,
                    code: encodeURIComponent(s),
                    expire_day: a,
                    mobile: encodeURIComponent(n),
                    sex: g,
                    password: KgUser.Md5(c),
                    nickname: encodeURIComponent(d),
                    error: t.error,
                    callback: r,
                    mid: u,
                    plat: m,
                    dfid: K
                }
            } else l = {
                regtype: "mobile",
                appid: i,
                code: encodeURIComponent(s),
                mobile: encodeURIComponent(n),
                callback: r,
                error: t.error,
                type: p,
                mid: u,
                plat: m,
                dfid: K
            };
            window.tempCallbackName = function (e) {
                20028 == e.errorCode ? 1001 == i ? KgUser.antiBrushPCCLient({
                    eventid: e.eventid,
                    userid: 0,
                    appid: 1001,
                    mid: e.mid,
                    url: window.location.href,
                    callback: function (e) {
                        console.log("res", e), 1 == e.status ? (console.log("success"), KgUser.RegByMobile(window.saveTempInfoObj, o)) : console.log("fail")
                    }
                }) : 1058 == i ? (localStorage.setItem("_RIS_PH", n), localStorage.setItem("_RIS_PS", c), KgUser.antiBrushH5({
                    eventid: e.eventid,
                    userid: 0,
                    appid: 1058,
                    mid: e.mid,
                    url: window.location.href,
                    callback: function (e) {
                        1 == e.status ? console.log("success") : console.log("fail")
                    }
                })) : KgUser.antiBrush({
                    eventid: e.eventid,
                    userid: 0,
                    appid: i,
                    mid: e.mid,
                    url: window.location.href,
                    callback: function (e) {
                        1 == e.status && (console.log("success"), KgUser.RegByMobile(window.saveTempInfoObj, o))
                    }
                }) : KgUser.GetMsg(o, e)
            }, KgUser.loadScript(KgUser.KgUrl.login + "v2/reg", l)
        })
    },
    RegByEmail: function (e, t) {
        var o, i, s, n, a = 1, g = 1, c = "";
        if (1 == KgUser.CodeAll.codetype && (e.code = KgUser.CodeAll.cval), KgUser.IsEmpty(e.appid) || KgUser.IsEmpty(KgUser.Trim(e.email)) || KgUser.IsEmpty(KgUser.Trim(e.password))) return KgUser.GetMsg(t, {
            errorCode: "20010",
            errorMsg: "必填参数不能为空"
        }), !1;
        if (KgUser.IsEmpty(KgUser.Trim(e.code)) && 2 != KgUser.CodeAll.codetype) return KgUser.GetMsg(t, {
            errorCode: "20010",
            errorMsg: "验证码不能为空"
        }), !1;
        o = e.appid, s = KgUser.Trim(e.email), n = KgUser.Trim(e.password), i = KgUser.Trim(e.code), KgUser.IsEmpty(e.expire_day) || (a = e.expire_day), void 0 !== e.sex && null != e.sex && (g = e.sex), c = KgUser.IsEmpty(KgUser.Trim(e.nickname)) ? s : KgUser.Trim(e.nickname);
        var d = KgUser.getPlat(o);
        KgUser.getDfidAsyn(o, function (e) {
            var r = {
                regtype: "email",
                appid: o,
                code: encodeURIComponent(i),
                expire_day: a,
                email: encodeURIComponent(s),
                sex: g,
                password: KgUser.Md5(n),
                nickname: encodeURIComponent(c),
                callback: t,
                codetype: KgUser.CodeAll.codetype,
                plat: d,
                dfid: ""
            };
            2 == KgUser.CodeAll.codetype && void 0 !== KgUser.$C("geetest_challenge", "", "input")[0] && (r.geetest_challenge = KgUser.$C("geetest_challenge", KgUser.CodeAll.codeid, "input")[0].value, r.geetest_validate = KgUser.$C("geetest_validate", KgUser.CodeAll.codeid, "input")[0].value, r.geetest_seccode = KgUser.$C("geetest_seccode", KgUser.CodeAll.codeid, "input")[0].value), KgUser.loadScript(KgUser.KgUrl.login + "v2/reg/", r)
        })
    },
    CheckPwd: function (e, r) {
        var t, o, i = "";
        if (KgUser.IsEmpty(e.appid) || KgUser.IsEmpty(KgUser.Trim(e.password))) return KgUser.GetMsg(r, {
            errorCode: "20010",
            errorMsg: "必填参数不能为空"
        }), !1;
        t = e.appid, o = KgUser.Trim(e.password), KgUser.IsEmpty(KgUser.Trim(e.username)) || (i = KgUser.Trim(e.username));
        var s = {appid: t, str: KgUser.Md5(o), username: encodeURIComponent(i), callback: r};
        KgUser.loadScript(KgUser.KgUrl.user + "check_str/", s)
    },
    CheckReg: function (e, r) {
        setTimeout(function () {
            KgUser.GetMsg(r, {data: 0})
        }, 200)
    },
    ThirdCode: function (e, r) {
        if (!KgUser.IsEmpty(KgUser.Trim(e.url)) && !KgUser.IsEmpty(KgUser.Trim(e.id))) {
            var t = e.url, o = e.id;
            window.gt_thirdcode = (s = window, n = document, a = n.getElementsByTagName("head")[0], function (t, e) {
                t.protocol = t.https ? "https://" : "http://";
                var o = function () {
                    e(new s.Geetest(t))
                }, r = function () {
                    var e = n.createElement("script");
                    e.id = "gt_lib", e.src = t.protocol + "static.geetest.com/static/js/geetest.0.0.0.js", e.charset = "UTF-8", e.type = "text/javascript", a.appendChild(e);
                    var r = !1;
                    e.onload = e.onreadystatechange = function () {
                        r || this.readyState && "loaded" !== this.readyState && "complete" !== this.readyState || (o(), r = !0)
                    }
                };
                if (s.geetest_callback = function () {
                    o()
                }, t) if (t.offline) r(); else if (s.Geetest) o(); else {
                    var i = n.createElement("script");
                    i.onerror = function () {
                        t.offline = !0, r()
                    }, i.src = t.protocol + "api.geetest.com/get.php?callback=geetest_callback", a.appendChild(i)
                }
            }), KgUser.IsEmpty(KgUser.Trim(o)) || (KgUser.loadScript(t, ""), window.gt_succCallback = function (e) {
                void 0 !== KgUser.$(o) && (KgUser.$(o).innerHTML = ""), e.appendTo("#" + o), e.onSuccess(r)
            })
        }
        var s, n, a
    },
    CodeAll: {kg_sudoku_anslist: ["", "", "", ""], kg_sudoku_flag: 0, codetype: 0, cval: "", appid: 0, codeid: ""},
    SudokuCode: function (e, n) {
        if (!KgUser.IsEmpty(KgUser.Trim(e.url)) && !KgUser.IsEmpty(KgUser.Trim(e.id))) {
            KgUser.CodeAll.cval = "", KgUser.CodeAll.kg_sudoku_anslist = ["", "", "", ""], KgUser.CodeAll.kg_sudoku_flag = 0;
            var r = document.createElement("link"), t = document.head || document.getElementsByTagName("head")[0];
            r.type = "text/css", r.rel = "stylesheet", r.href = KgUser.KgUrl.verify + "v1/static/css/sudoku.css", r.id = "kg_sudoku_css", void 0 !== document.getElementById("kg_sudoku_css") && null != document.getElementById("kg_sudoku_css") || t.appendChild(r);
            var o = e.url, i = e.id, a = e.appid;
            KgUser.CodeAll.appid = a;
            var s = ".kg_sudoku_val_add b,.kg_sudoku_input_show span,.kg_sudoku_val_input a b{background:url(" + o + ") no-repeat  -500px -500px;} .kg_sudoku_input_show span{background-position:0 0;}";
            KgUser.LoadCss(s, "kg_sudoku_style");
            var g = ['<div id="kg_sudoku_com" class="kg_sudoku_com">', '<div class="kg_sudoku_val_add kg_sudoku_clearfix" id="kg_sudoku_val_add">', "<b></b>", "<b></b>", "<b></b>", "<b></b>", '<b class="kg_sudoku_delete" title="清除" onclick="KgUser.DelSudokuCode(1);"></b>', '<div id="kg_sudoku_msg" class="kg_sudoku_msg"></div>', "</div>", '<div class="kg_sudoku_input_show"><span></span><a onclick="KgUser.ChangeSudokuCode(\'' + o + "',1)\">看不清,换一张?</a></div>", '<div class="kg_sudoku_ation">点击框内文字输入上图中对应<i>汉字</i></div>', '<div class="kg_sudoku_val_input kg_sudoku_clearfix" id="kg_sudoku_val_input">', '<a href="javascript:;"><b id="kg_sudoku_v_0"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_1"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_2"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_3"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_4"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_5"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_6"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_7"></b></a>', '<a href="javascript:;"><b id="kg_sudoku_v_8"></b></a>', "</div>", "</div>"].join("");
            document.getElementById(i).innerHTML = g;
            var c = function (e, r) {
                for (var t = KgUser.CodeAll.kg_sudoku_anslist, o = KgUser.CodeAll.kg_sudoku_flag, i = document.getElementById("kg_sudoku_val_add").getElementsByTagName("b"), s = 0; s < t.length; s++) if ("" == t[s]) {
                    t[s] = r, i[s].style.cssText = "background-position:" + e, o = s;
                    break
                }
                3 == o && (KgUser.CodeAll.cval = t.join(""), setTimeout(function () {
                    KgUser.CheckCode({
                        appid: a,
                        code: KgUser.CodeAll.cval,
                        type: "RegCheckCode",
                        blurCallback: n
                    }, "KgUser.CheckSudoCode")
                }, 100))
            };
            !function (e, r, t) {
                for (var o = document.getElementById(e).getElementsByTagName("b"), i = 0; i < o.length; i++) o[i].onclick = function () {
                    c_val = parseInt(this.id.replace("kg_sudoku_v_", "")) + 1;
                    var e, r, t, o, i,
                        s = (e = this.id, r = "backgroundPosition", t = document.getElementById(e), o = document.documentMode, "backgroundPosition" == r && 10 == o ? t.currentStyle.backgroundPositionX + " " + t.currentStyle.backgroundPositionY : t.currentStyle ? t.currentStyle[r] : window.getComputedStyle ? (propprop = r.replace(/([A-Z])/g, "-$1"), propprop = r.toLowerCase(), document.defaultView.getComputedStyle(t, null)[r]) : void 0).split(" "),
                        n = "";
                    KgUser.IsEmpty(s[0]) || (i = parseInt(s[0].replace("px")) - 3), KgUser.IsEmpty(s[1]) || (n = parseInt(s[1].replace("px")) - 5), c(i + "px " + n + "px", c_val)
                }
            }("kg_sudoku_val_input")
        }
    },
    ChangeSudokuCode: function (r, t) {
        setTimeout(function () {
            var e = ".kg_sudoku_val_add b,.kg_sudoku_input_show span,.kg_sudoku_val_input a b{background:url(" + r + "&nt=" + (new Date).getTime() + ") no-repeat -500px -500px;}  .kg_sudoku_input_show span{background-position:0 0;}";
            KgUser.LoadCss(e, "kg_sudoku_style"), KgUser.DelSudokuCode(t)
        }, 200)
    },
    DelSudokuCode: function (e) {
        KgUser.CodeAll.cval = "", KgUser.CodeAll.kg_sudoku_flag = 0, KgUser.CodeAll.kg_sudoku_anslist = ["", "", "", ""];
        for (var r = document.getElementById("kg_sudoku_val_add").getElementsByTagName("b"), t = 0; t < r.length; t++) r[t].style.cssText = "";
        1 == e && (document.getElementById("kg_sudoku_msg").className = "kg_sudoku_msg")
    },
    CheckSudoCode: function (e) {
        var r = "";
        if (KgUser.IsEmpty(e.errorCode)) r = "kg_sudoku_msg success", document.getElementById("kg_sudoku_msg").className = r; else {
            r = "kg_sudoku_msg fail", document.getElementById("kg_sudoku_msg").className = r;
            var t = KgUser.KgUrl.verify + "v1/get_img_code?type=RegCheckCode&appid=" + KgUser.CodeAll.appid + "&codetype=1&t=" + (new Date).getTime();
            KgUser.ChangeSudokuCode(t, 0)
        }
    },
    CheckCode: function (e, r) {
        var t = 0, o = parseInt((new Date).getTime() / 1e3), i = "", s = "LoginCheckCode", n = "";
        if (KgUser.IsEmpty(e.appid)) return KgUser.GetMsg(r, {errorCode: "20010", errorMsg: "必填参数不能为空"}), !1;
        if (!KgUser.IsEmpty(KgUser.Trim(e.code))) {
            i = KgUser.Trim(e.code), t = e.appid, KgUser.IsEmpty(KgUser.Trim(e.type)) || (s = KgUser.Trim(e.type)), KgUser.IsEmpty(e.blurCallback) || (n = e.blurCallback);
            var a = {appid: t, ct: o, code: encodeURIComponent(i), type: encodeURIComponent(s), callback: r};
            KgUser.loadScript(KgUser.KgUrl.login + "v1/check_img_code", a, n)
        }
    },
    Md5: function (e) {
        var r, i = 0, s = 8;

        function a(e, r, t, o, i, s) {
            return K((n = K(K(r, e), K(o, s))) << (a = i) | n >>> 32 - a, t);
            var n, a
        }

        function l(e, r, t, o, i, s, n) {
            return a(r & t | ~r & o, e, r, i, s, n)
        }

        function u(e, r, t, o, i, s, n) {
            return a(r & o | t & ~o, e, r, i, s, n)
        }

        function p(e, r, t, o, i, s, n) {
            return a(r ^ t ^ o, e, r, i, s, n)
        }

        function m(e, r, t, o, i, s, n) {
            return a(t ^ (r | ~o), e, r, i, s, n)
        }

        function K(e, r) {
            var t = (65535 & e) + (65535 & r);
            return (e >> 16) + (r >> 16) + (t >> 16) << 16 | 65535 & t
        }

        return e = e ? function (e) {
            for (var r = i ? "0123456789ABCDEF" : "0123456789abcdef", t = "", o = 0; o < 4 * e.length; o++) t += r.charAt(e[o >> 2] >> o % 4 * 8 + 4 & 15) + r.charAt(e[o >> 2] >> o % 4 * 8 & 15);
            return t
        }(function (e, r) {
            e[r >> 5] |= 128 << r % 32, e[14 + (r + 64 >>> 9 << 4)] = r;
            for (var t = 1732584193, o = -271733879, i = -1732584194, s = 271733878, n = 0; n < e.length; n += 16) {
                var a = t, g = o, c = i, d = s;
                t = l(t, o, i, s, e[n + 0], 7, -680876936), s = l(s, t, o, i, e[n + 1], 12, -389564586), i = l(i, s, t, o, e[n + 2], 17, 606105819), o = l(o, i, s, t, e[n + 3], 22, -1044525330), t = l(t, o, i, s, e[n + 4], 7, -176418897), s = l(s, t, o, i, e[n + 5], 12, 1200080426), i = l(i, s, t, o, e[n + 6], 17, -1473231341), o = l(o, i, s, t, e[n + 7], 22, -45705983), t = l(t, o, i, s, e[n + 8], 7, 1770035416), s = l(s, t, o, i, e[n + 9], 12, -1958414417), i = l(i, s, t, o, e[n + 10], 17, -42063), o = l(o, i, s, t, e[n + 11], 22, -1990404162), t = l(t, o, i, s, e[n + 12], 7, 1804603682), s = l(s, t, o, i, e[n + 13], 12, -40341101), i = l(i, s, t, o, e[n + 14], 17, -1502002290), o = l(o, i, s, t, e[n + 15], 22, 1236535329), t = u(t, o, i, s, e[n + 1], 5, -165796510), s = u(s, t, o, i, e[n + 6], 9, -1069501632), i = u(i, s, t, o, e[n + 11], 14, 643717713), o = u(o, i, s, t, e[n + 0], 20, -373897302), t = u(t, o, i, s, e[n + 5], 5, -701558691), s = u(s, t, o, i, e[n + 10], 9, 38016083), i = u(i, s, t, o, e[n + 15], 14, -660478335), o = u(o, i, s, t, e[n + 4], 20, -405537848), t = u(t, o, i, s, e[n + 9], 5, 568446438), s = u(s, t, o, i, e[n + 14], 9, -1019803690), i = u(i, s, t, o, e[n + 3], 14, -187363961), o = u(o, i, s, t, e[n + 8], 20, 1163531501), t = u(t, o, i, s, e[n + 13], 5, -1444681467), s = u(s, t, o, i, e[n + 2], 9, -51403784), i = u(i, s, t, o, e[n + 7], 14, 1735328473), o = u(o, i, s, t, e[n + 12], 20, -1926607734), t = p(t, o, i, s, e[n + 5], 4, -378558), s = p(s, t, o, i, e[n + 8], 11, -2022574463), i = p(i, s, t, o, e[n + 11], 16, 1839030562), o = p(o, i, s, t, e[n + 14], 23, -35309556), t = p(t, o, i, s, e[n + 1], 4, -1530992060), s = p(s, t, o, i, e[n + 4], 11, 1272893353), i = p(i, s, t, o, e[n + 7], 16, -155497632), o = p(o, i, s, t, e[n + 10], 23, -1094730640), t = p(t, o, i, s, e[n + 13], 4, 681279174), s = p(s, t, o, i, e[n + 0], 11, -358537222), i = p(i, s, t, o, e[n + 3], 16, -722521979), o = p(o, i, s, t, e[n + 6], 23, 76029189), t = p(t, o, i, s, e[n + 9], 4, -640364487), s = p(s, t, o, i, e[n + 12], 11, -421815835), i = p(i, s, t, o, e[n + 15], 16, 530742520), o = p(o, i, s, t, e[n + 2], 23, -995338651), t = m(t, o, i, s, e[n + 0], 6, -198630844), s = m(s, t, o, i, e[n + 7], 10, 1126891415), i = m(i, s, t, o, e[n + 14], 15, -1416354905), o = m(o, i, s, t, e[n + 5], 21, -57434055), t = m(t, o, i, s, e[n + 12], 6, 1700485571), s = m(s, t, o, i, e[n + 3], 10, -1894986606), i = m(i, s, t, o, e[n + 10], 15, -1051523), o = m(o, i, s, t, e[n + 1], 21, -2054922799), t = m(t, o, i, s, e[n + 8], 6, 1873313359), s = m(s, t, o, i, e[n + 15], 10, -30611744), i = m(i, s, t, o, e[n + 6], 15, -1560198380), o = m(o, i, s, t, e[n + 13], 21, 1309151649), t = m(t, o, i, s, e[n + 4], 6, -145523070), s = m(s, t, o, i, e[n + 11], 10, -1120210379), i = m(i, s, t, o, e[n + 2], 15, 718787259), o = m(o, i, s, t, e[n + 9], 21, -343485551), t = K(t, a), o = K(o, g), i = K(i, c), s = K(s, d)
            }
            return Array(t, o, i, s)
        }(function (e) {
            for (var r = Array(), t = (1 << s) - 1, o = 0; o < e.length * s; o += s) r[o >> 5] |= (e.charCodeAt(o / s) & t) << o % 32;
            return r
        }(r = e), r.length * s)) : ""
    },
    Trim: function (e) {
        return "string" == typeof e ? e.replace(/^\s+|\s+$/g, "") : e
    },
    IsEmpty: function (e) {
        return void 0 === e || null == e || "" == e
    },
    GetBytes: function () {
        for (var e = 0, r = 0; r < this.length; r++) 256 < this.charCodeAt(r) ? e += 2 : e += 1;
        return e
    },
    Intercept: function (e, r) {
        var t = this;
        if ((t = t.trim()).getBytes() < e) return t;
        var o = 0, i = 0;
        0 < r.length && (e -= r.length);
        for (var s = 0; s < t.length && (256 < this.charCodeAt(s) ? o += 2 : o += 1, !(e < o)); s++) i++;
        return t.substr(0, i) + r
    },
    Guid: function () {
        function e() {
            return (65536 * (1 + Math.random()) | 0).toString(16).substring(1)
        }

        return e() + e() + "-" + e() + "-" + e() + "-" + e() + "-" + e() + e() + e()
    },
    CheckAutoLogin: function () {
        KgUser.Cookie.read("KuGoo", "KugooID");
        var e = KgUser.Cookie.read("KuGoo", "ct");
        86400 < parseInt((new Date).getTime() / 1e3) - e && KgUser.AutoLogin("")
    },
    LoginQRCodeImgObject: null,
    GetQRCode: function (i, e, s, n, a, g, c, d) {
        var r = {
                appid: i,
                uuid: KgUser.Cookie.read(KgUser.KgMid.name),
                clientver: e,
                clienttime: parseInt((new Date).getTime() / 1e3),
                type: 1
            }, t = KgUser.signatureParam(r),
            o = "appid=" + r.appid + "&clientver=" + r.clientver + "&clienttime=" + r.clienttime + "&uuid=" + r.uuid + "&type=" + r.type + "&signature=" + t;
        KgUser.sentGetRequest(KgUser.KgUrl.login + "v1/qrcode", o, function (r) {
            var t = "";
            try {
                t = KgUser.JSON.parse(r)
            } catch (e) {
                t = r
            }
            var e = {};
            if (1 != t.status || KgUser.IsEmpty(t.data.qrcode)) e.status = 0, e.data = {
                appid: i,
                qrcode: null
            }, e.error_code = t.error_code, d && d(KgUser.JSON.stringify(e)); else {
                KgUser.QRCodeState = 1;
                var o = KgUser.KgUrl.qrcodeH5 + "?qrcode=" + t.data.qrcode + "&appid=" + i;
                KgUser.loadScript("https://staticssl.kugou.com/common/js/min/login/qrcode.min.js", null, function () {
                    setTimeout(function () {
                        null == KgUser.LoginQRCodeImgObject ? KgUser.LoginQRCodeImgObject = new QRCode(s, {
                            text: o,
                            width: n,
                            height: a,
                            colorDark: g,
                            colorLight: c,
                            correctLevel: QRCode.CorrectLevel.L
                        }) : (KgUser.LoginQRCodeImgObject.clear(), KgUser.LoginQRCodeImgObject.makeCode(o)), s.removeAttribute("title"), e.status = 1, e.data = {
                            appid: i,
                            qrcode: t.data.qrcode
                        }, e.error_code = 0, d && d(KgUser.JSON.stringify(e))
                    }, 100)
                })
            }
        })
    },
    GetQRCodeStateTimer: null,
    GetQRCodeStatePollingTime: 0,
    QRCodeState: 1,
    GetQRCodeState: function (s, n, a, g) {
        var e, c = function (o, i) {
            if (4 == (r = KgUser.QRCodeState) || 0 == r) clearTimeout(KgUser.GetQRCodeStateTimer); else {
                var e = 1e3;
                60 < KgUser.GetQRCodeStatePollingTime && (e = 5e3), KgUser.GetQRCodeStateTimer = setTimeout(function () {
                    KgUser.GetQRCodeStatePollingTime++;
                    var e = {
                            appid: s,
                            uuid: KgUser.Cookie.read(KgUser.KgMid.name),
                            clientver: n,
                            clienttime: parseInt((new Date).getTime() / 1e3),
                            qrcode: a,
                            dfid: o,
                            plat: i
                        }, r = KgUser.signatureParam(e),
                        t = "appid=" + e.appid + "&clientver=" + e.clientver + "&clienttime=" + e.clienttime + "&uuid=" + e.uuid + "&qrcode=" + e.qrcode + "&dfid=" + e.dfid + "&plat=" + e.plat + "&signature=" + r;
                    KgUser.sentGetRequest(KgUser.KgUrl.login + "v1/get_userinfo_qrcode", t, function (r) {
                        var t = "";
                        try {
                            t = KgUser.JSON.parse(r)
                        } catch (e) {
                            t = r
                        }
                        1 == t.status && (KgUser.QRCodeState = t.data.status, c(o, i)), g && g(r)
                    })
                }, e)
            }
            var r
        };
        if (null != KgUser.GetQRCodeStateTimer && (clearTimeout(KgUser.GetQRCodeStateTimer), KgUser.GetQRCodeStatePollingTime = 0), 1 == (e = KgUser.QRCodeState) || 2 == e) {
            var r = KgUser.getPlat(s);
            KgUser.getDfidAsyn(s, function (e) {
                c(e, r)
            })
        }
    },
    ClearGetQRCodeStateTimer: function () {
        clearTimeout(KgUser.GetQRCodeStateTimer)
    },
    QRCodeLogin: function (t, o, i, s) {
        var n = KgUser.getPlat(t), a = "";
        KgUser.getDfidAsyn(t, function (e) {
            a = e;
            var r = {
                a_id: t,
                userid: o,
                t: i,
                ct: parseInt((new Date).getTime() / 1e3),
                callback: s,
                error: s,
                domain: KgUser.GetDomain(),
                uuid: KgUser.Cookie.read(KgUser.KgMid.name),
                mid: KgUser.Cookie.read(KgUser.KgMid.name),
                plat: n,
                dfid: a
            };
            KgUser.IsEmpty(i) || KgUser.loadScript(KgUser.KgUrl.login + "v1/autologin", r)
        })
    },
    signatureParam: function (e) {
        var r = new Array;
        for (var t in e) e.hasOwnProperty(t) && "signature" != t && r.push(e[t]);
        for (var o = r.sort(), i = "", s = 0, n = o.length; s < n; s++) i += o[s];
        return i = KgUser.Md5(i)
    },
    sentGetRequest: function (r, t, o) {
        var i, s = null, n = !1, a = !1, e = r.split("/");
        if (e.length <= 2) args.error && args.error(KgUser.KgErrType.param); else {
            var g = e[2], c = !1;
            if ("login-user.kugou.com" == g && (c = !0), window.XMLHttpRequest && !window.ActiveXObject) i = new XMLHttpRequest; else if (window.ActiveXObject) try {
                i = new ActiveXObject("Microsoft.XMLHTTP")
            } catch (e) {
                try {
                    i = new ActiveXObject("Msxml2.XMLHTTP")
                } catch (e) {
                    i = null
                }
            }
            i.open("GET", r + "?" + t, !0), i.send(null), i.onreadystatechange = function () {
                if (4 == i.readyState) {
                    var e = i.status;
                    200 <= e && e < 300 || 304 == e ? (a = !0, clearTimeout(s), o(i.responseText, i.responseXML)) : c ? n || (clearTimeout(s), i.abort(), KgUser.sentGetRequestRetry(r, g, t, o)) : o(e)
                }
            }, c && (s = setTimeout(function () {
                a || (n = !0, i.abort(), KgUser.sentGetRequestRetry(r, g, t, o))
            }, 3500))
        }
    },
    sentPostRequest: function (r, t, o) {
        var i, s = null, n = !1, a = !1, e = r.split("/");
        if (e.length <= 2) args.error && args.error(KgUser.KgErrType.param); else {
            var g = e[2], c = !1;
            if ("login-user.kugou.com" == g && (c = !0), window.XMLHttpRequest && !window.ActiveXObject) i = new XMLHttpRequest; else if (window.ActiveXObject) try {
                i = new ActiveXObject("Microsoft.XMLHTTP")
            } catch (e) {
                try {
                    i = new ActiveXObject("Msxml2.XMLHTTP")
                } catch (e) {
                    i = null
                }
            }
            i.open("POST", r, !0), KgUser.IsEmpty(t) ? i.send(null) : i.send(JSON.stringify(t)), i.onreadystatechange = function () {
                if (4 == i.readyState) {
                    var e = i.status;
                    200 <= e && e < 300 || 304 == e ? (a = !0, clearTimeout(s), o(i.responseText, i.responseXML)) : c ? n || (clearTimeout(s), i.abort(), KgUser.sentPostRequestRetry(r, g, t, o)) : o(e)
                }
            }, c && (s = setTimeout(function () {
                a || (n = !0, i.abort(), KgUser.sentPostRequestRetry(r, g, t, o))
            }, 3500))
        }
    },
    loginDRRetry: function (e, r, t, o) {
        var i = "login-user.kugou.com", s = "loginuserretry.kugou.com";
        if (!KgUser.IsEmpty(s)) return KgUser.isAckHost(KgUser.KgUrl.login, i) || (KgUser.KgUrl.login = KgUser.KgUrl.login.replace(i, s), KgUser.KgUrl.loginHTTP = KgUser.KgUrl.loginHTTP.replace(i, s)), e = e.replace(r, s), void KgUser.loadScriptN(e, t, o)
    },
    sentGetRequestRetry: function (e, r, t, o) {
        var i = "login-user.kugou.com", s = "loginuserretry.kugou.com";
        if (!KgUser.IsEmpty(s)) return KgUser.isAckHost(KgUser.KgUrl.login, i) || (KgUser.KgUrl.login = KgUser.KgUrl.login.replace(i, s), KgUser.KgUrl.loginHTTP = KgUser.KgUrl.loginHTTP.replace(i, s)), e = e.replace(r, s), void KgUser.sentGetRequest(e, t, o)
    },
    sentPostRequestRetry: function (e, r, t, o) {
        var i = "login-user.kugou.com", s = "loginuserretry.kugou.com";
        if (!KgUser.IsEmpty(s)) return KgUser.isAckHost(KgUser.KgUrl.login, i) || (KgUser.KgUrl.login = KgUser.KgUrl.login.replace(i, s), KgUser.KgUrl.loginHTTP = KgUser.KgUrl.loginHTTP.replace(i, s)), e = e.replace(r, s), void KgUser.sentPostRequest(e, t, o)
    },
    antiBrush: function (r) {
        if (!KgUser.IsEmpty(r.eventid) && !KgUser.IsEmpty(r.appid)) {
            var t = "kgAntiBrush" + Math.random().toString().substring(5);
            r.callback ? window[t] = r.callback : window[t] = function () {
                return null
            };
            try {
                jQuery || KgUser.loadScript("https://staticssl.kugou.com/common/js/min/jquery-2.1.4.min.js", null)
            } catch (e) {
                KgUser.loadScript("https://staticssl.kugou.com/common/js/min/jquery-2.1.4.min.js", null)
            }
            try {
                verifyObject.init(r.appid, r.mid, r.userid, r.eventid, t)
            } catch (e) {
                KgUser.loadScript("https://staticssl.kugou.com/verify/static/js/verify.min.js?20190508", null, function () {
                    setTimeout(function () {
                        verifyObject.init(r.appid, r.mid, r.userid, r.eventid, t)
                    }, 100)
                })
            }
        }
    },
    antiBrushH5: function (r) {
        if (!KgUser.IsEmpty(r.eventid) && !KgUser.IsEmpty(r.appid)) {
            var t = "kgAntiBrush" + Math.random().toString().substring(5);
            r.callback ? window[t] = r.callback : window[t] = function () {
                return null
            };
            try {
                jQuery || KgUser.loadScript("https://staticssl.kugou.com/common/js/min/jquery-2.1.4.min.js", null)
            } catch (e) {
                KgUser.loadScript("https://staticssl.kugou.com/common/js/min/jquery-2.1.4.min.js", null)
            }
            try {
                verifyObjectH5.init(r.appid, r.mid, r.userid, r.eventid, r.url, t)
            } catch (e) {
                KgUser.loadScript("https://staticssl.kugou.com/verify/static/js/verify.h5.min.js?20190212", null, function () {
                    setTimeout(function () {
                        verifyObjectH5.init(r.appid, r.mid, r.userid, r.eventid, r.url, t)
                    }, 100)
                })
            }
        }
    },
    antiBrushPCCLient: function (r) {
        if (!KgUser.IsEmpty(r.eventid)) {
            var t = "kgAntiBrush" + Math.random().toString().substring(5);
            r.callback ? window[t] = r.callback : window[t] = function () {
                return null
            };
            try {
                jQuery ? o(function () {
                    i(r.eventid, t)
                }) : KgUser.loadScript("https://staticssl.kugou.com/common/js/min/jquery-2.1.4.min.js", null, function () {
                    o(function () {
                        i(r.eventid, t)
                    })
                })
            } catch (e) {
                KgUser.loadScript("https://staticssl.kugou.com/common/js/min/jquery-2.1.4.min.js", null, function () {
                    o(function () {
                        i(r.eventid, t)
                    })
                })
            }

            function o(r) {
                try {
                    riskCtrl ? r && r() : KgUser.loadScript("https://staticssl.kugou.com/common/js/min/riskCtrl.min.js", null, function () {
                        r && r()
                    })
                } catch (e) {
                    KgUser.loadScript("https://staticssl.kugou.com/common/js/min/riskCtrl.min.js", null, function () {
                        r && r()
                    })
                }
            }

            function i(e, r) {
                try {
                    riskCtrl.init({
                        eventId: e, callback: function (e) {
                            window[r](e)
                        }
                    })
                } catch (e) {
                }
            }
        }
    },
    getDfid: function (e) {
        return KgUser.Cookie.read("kg_dfid")
    },
    getDfidInClient: function (e, r) {
        if (1001 == e) {
            !function (r) {
                var t = "KGSupercall_GetUserInfo" + Math.random().toString().substr(2, 9), e = null;
                window[t] = function (e) {
                    r && r(e), window[t] = null
                };
                try {
                    e = external.SuperCall(504, '{"callback":"' + t + '"}')
                } catch (e) {
                }
                if (void 0 === e || !r) return r || (window[t] = null);
                window[t](e)
            }(function (e) {
                "string" == typeof e && (e = JSON.parse(e)), dfid = e.dfid, dfid || (dfid = ""), r && r(dfid)
            })
        }
    },
    getDfidAsyn: function (e, r) {
        var t = "";
        1001 == e ? KgUser.getDfidInClient(e, function (e) {
            t = e, r && r(t)
        }) : (t = KgUser.getDfid(e), r && r(t))
    },
    getPlat: function (e) {
        var r = 4;
        return 1001 == e && (r = 3), r
    },
    getKgMid: function () {
        var e = KgUser.Cookie.read(KgUser.KgMid.name);
        if (navigator.cookieEnabled) {
            if (KgUser.IsEmpty(e)) {
                var r = KgUser.Guid();
                e = KgUser.Md5(r);
                try {
                    KgUser.Cookie.write(KgUser.KgMid.name, KgUser.Md5(r), 86400 * KgUser.KgMid.days, "/", KgUser.GetDomain())
                } catch (e) {
                }
            }
        } else {
            var t = navigator.userAgent, o = function () {
                    var e = navigator.plugins, r = "";
                    if (0 < e.length) {
                        for (var t = [], o = 0, i = e.length; o < i; o++) {
                            var s = e[o].name;
                            t.push(s)
                        }
                        r = t.toString()
                    }
                    return r
                }(), i = screen.width + "x" + screen.height, s = screen.colorDepth ? screen.colorDepth : "",
                n = screen.pixelDepth ? screen.pixelDepth : "", a = function () {
                    var e = ["canvas"];
                    try {
                        var r = document.createElement("canvas");
                        if (r.getContext && r.getContext("2d")) {
                            r.width = 200, r.height = 200, r.style.display = "inline";
                            var t = r.getContext("2d");
                            t.rect(0, 0, 10, 10), t.rect(2, 2, 6, 6), e.push("canvas winding:" + (!1 === t.isPointInPath(5, 5, "evenodd") ? "yes" : "no")), t.textBaseline = "alphabetic", t.fillStyle = "#f60", t.fillRect(125, 1, 62, 20), t.fillStyle = "#069", t.font = "14px 'Arial'", t.fillText("hello kugou", 2, 15), t.fillStyle = "rgba(102, 204, 0, 0.2)", t.font = "18pt Arial", t.fillText("hello kugou", 4, 45), t.globalCompositeOperation = "multiply", t.fillStyle = "rgb(255,0,255)", t.beginPath(), t.arc(50, 50, 50, 0, 2 * Math.PI, !0), t.closePath(), t.fill(), t.fillStyle = "rgb(0,255,255)", t.beginPath(), t.arc(100, 50, 50, 0, 2 * Math.PI, !0), t.closePath(), t.fill(), t.fillStyle = "rgb(255,255,0)", t.beginPath(), t.arc(75, 100, 50, 0, 2 * Math.PI, !0), t.closePath(), t.fill(), t.fillStyle = "rgb(255,0,255)", t.arc(75, 75, 75, 0, 2 * Math.PI, !0), t.arc(75, 75, 25, 0, 2 * Math.PI, !0), t.fill("evenodd"), r.toDataURL && e.push("canvas fp:" + r.toDataURL())
                        }
                    } catch (e) {
                    }
                    return KgUser.Md5(e.toString())
                }();
            e = KgUser.Md5(t + o + i + s + n + a)
        }
        return e
    }
}, m_val = KgUser.Cookie.read(KgUser.KgMid.name);
if (KgUser.Cookie.remove("kg_mid_temp"), KgUser.Cookie.write("kg_mid_temp", m_val, 300, "/", KgUser.GetDomain()), KgUser.IsEmpty(m_val)) {
    var n = KgUser.Guid();
    KgUser.Cookie.write(KgUser.KgMid.name, KgUser.Md5(n), 86400 * KgUser.KgMid.days, "/", KgUser.GetDomain())
}
"kugou.com" != KgUser.GetDomain() ? KgUser.CrossDomainLogin("") : KgUser.CheckAutoLogin();
try {
    document.execCommand("BackgroundImageCache", !1, !0)
} catch (e) {
}
KgUser.loadAckJs();