/* eslint-disable */

;(function() {
  var k
  function l(a) {
    var b = 0
    return function() {
      return b < a.length ? { done: !1, value: a[b++] } : { done: !0 }
    }
  }
  var m =
      'function' == typeof Object.defineProperties
        ? Object.defineProperty
        : function(a, b, e) {
            a != Array.prototype && a != Object.prototype && (a[b] = e.value)
          },
    n =
      'undefined' != typeof window && window === this
        ? this
        : 'undefined' != typeof global && null != global
        ? global
        : this
  function p() {
    p = function() {}
    n.Symbol || (n.Symbol = r)
  }
  var r = (function() {
    var a = 0
    return function(b) {
      return 'jscomp_symbol_' + (b || '') + a++
    }
  })()
  function u() {
    p()
    var a = n.Symbol.iterator
    a || (a = n.Symbol.iterator = n.Symbol('iterator'))
    'function' != typeof Array.prototype[a] &&
      m(Array.prototype, a, {
        configurable: !0,
        writable: !0,
        value: function() {
          return v(l(this))
        }
      })
    u = function() {}
  }
  function v(a) {
    u()
    a = { next: a }
    a[n.Symbol.iterator] = function() {
      return this
    }
    return a
  }
  function x(a) {
    var b =
      'undefined' != typeof Symbol && Symbol.iterator && a[Symbol.iterator]
    return b ? b.call(a) : { next: l(a) }
  }
  var y
  if ('function' == typeof Object.setPrototypeOf) y = Object.setPrototypeOf
  else {
    var z
    a: {
      var A = { s: !0 },
        B = {}
      try {
        B.__proto__ = A
        z = B.s
        break a
      } catch (a) {}
      z = !1
    }
    y = z
      ? function(a, b) {
          a.__proto__ = b
          if (a.__proto__ !== b) throw new TypeError(a + ' is not extensible')
          return a
        }
      : null
  }
  var C = y
  function D() {
    this.h = !1
    this.c = null
    this.o = void 0
    this.b = 1
    this.m = this.u = 0
    this.g = null
  }
  function E(a) {
    if (a.h) throw new TypeError('Generator is already running')
    a.h = !0
  }
  D.prototype.i = function(a) {
    this.o = a
  }
  D.prototype.j = function(a) {
    this.g = { v: a, w: !0 }
    this.b = this.u || this.m
  }
  D.prototype['return'] = function(a) {
    this.g = { return: a }
    this.b = this.m
  }
  function F(a, b, e) {
    a.b = e
    return { value: b }
  }
  function G(a) {
    this.A = a
    this.l = []
    for (var b in a) this.l.push(b)
    this.l.reverse()
  }
  function H(a) {
    this.a = new D()
    this.B = a
  }
  H.prototype.i = function(a) {
    E(this.a)
    if (this.a.c) return I(this, this.a.c.next, a, this.a.i)
    this.a.i(a)
    return J(this)
  }
  function K(a, b) {
    E(a.a)
    var e = a.a.c
    if (e)
      return I(
        a,
        'return' in e
          ? e['return']
          : function(a) {
              return { value: a, done: !0 }
            },
        b,
        a.a['return']
      )
    a.a['return'](b)
    return J(a)
  }
  H.prototype.j = function(a) {
    E(this.a)
    if (this.a.c) return I(this, this.a.c['throw'], a, this.a.i)
    this.a.j(a)
    return J(this)
  }
  function I(a, b, e, c) {
    try {
      var d = b.call(a.a.c, e)
      if (!(d instanceof Object))
        throw new TypeError('Iterator result ' + d + ' is not an object')
      if (!d.done) return (a.a.h = !1), d
      var f = d.value
    } catch (g) {
      return (a.a.c = null), a.a.j(g), J(a)
    }
    a.a.c = null
    c.call(a.a, f)
    return J(a)
  }
  function J(a) {
    for (; a.a.b; )
      try {
        var b = a.B(a.a)
        if (b) return (a.a.h = !1), { value: b.value, done: !1 }
      } catch (e) {
        ;(a.a.o = void 0), a.a.j(e)
      }
    a.a.h = !1
    if (a.a.g) {
      b = a.a.g
      a.a.g = null
      if (b.w) throw b.v
      return { value: b['return'], done: !0 }
    }
    return { value: void 0, done: !0 }
  }
  function L(a) {
    this.next = function(b) {
      return a.i(b)
    }
    this['throw'] = function(b) {
      return a.j(b)
    }
    this['return'] = function(b) {
      return K(a, b)
    }
    u()
    this[Symbol.iterator] = function() {
      return this
    }
  }
  function M(a, b) {
    var e = new L(new H(b))
    C && C(e, a.prototype)
    return e
  }
  if (
    'function' === typeof Blob &&
    ('undefined' === typeof FormData || !FormData.prototype.keys)
  ) {
    var N = function(a, b) {
        for (var e = 0; e < a.length; e++) b(a[e])
      },
      O = function(a, b, e) {
        return b instanceof Blob
          ? [
              String(a),
              b,
              void 0 !== e
                ? e + ''
                : 'string' === typeof b.name
                ? b.name
                : 'blob'
            ]
          : [String(a), String(b)]
      },
      P = function(a, b) {
        if (a.length < b)
          throw new TypeError(
            b + ' argument required, but only ' + a.length + ' present.'
          )
      },
      Q = function(a) {
        var b = x(a)
        a = b.next().value
        b = b.next().value
        a instanceof Blob &&
          (a = new File([a], b, { type: a.type, lastModified: a.lastModified }))
        return a
      },
      R =
        'object' === typeof window
          ? window
          : 'object' === typeof self
          ? self
          : this,
      S = R.FormData,
      T = R.XMLHttpRequest && R.XMLHttpRequest.prototype.send,
      U = R.Request && R.fetch,
      V = R.navigator && R.navigator.sendBeacon
    p()
    var W = R.Symbol && Symbol.toStringTag
    W &&
      (Blob.prototype[W] || (Blob.prototype[W] = 'Blob'),
      'File' in R && !File.prototype[W] && (File.prototype[W] = 'File'))
    try {
      new File([], '')
    } catch (a) {
      R.File = function(b, e, c) {
        b = new Blob(b, c)
        c =
          c && void 0 !== c.lastModified ? new Date(c.lastModified) : new Date()
        Object.defineProperties(b, {
          name: { value: e },
          lastModifiedDate: { value: c },
          lastModified: { value: +c },
          toString: {
            value: function() {
              return '[object File]'
            }
          }
        })
        W && Object.defineProperty(b, W, { value: 'File' })
        return b
      }
    }
    p()
    u()
    var X = function(a) {
      this.f = Object.create(null)
      if (!a) return this
      var b = this
      N(a.elements, function(a) {
        if (a.name && !a.disabled && 'submit' !== a.type && 'button' !== a.type)
          if ('file' === a.type) {
            var c =
              a.files && a.files.length
                ? a.files
                : [new File([], '', { type: 'application/octet-stream' })]
            N(c, function(c) {
              b.append(a.name, c)
            })
          } else
            'select-multiple' === a.type || 'select-one' === a.type
              ? N(a.options, function(c) {
                  !c.disabled && c.selected && b.append(a.name, c.value)
                })
              : 'checkbox' === a.type || 'radio' === a.type
              ? a.checked && b.append(a.name, a.value)
              : ((c =
                  'textarea' === a.type
                    ? a.value.replace(/\r\n/g, '\n').replace(/\n/g, '\r\n')
                    : a.value),
                b.append(a.name, c))
      })
    }
    k = X.prototype
    k.append = function(a, b, e) {
      P(arguments, 2)
      var c = x(O.apply(null, arguments))
      a = c.next().value
      b = c.next().value
      e = c.next().value
      c = this.f
      c[a] || (c[a] = [])
      c[a].push([b, e])
    }
    k['delete'] = function(a) {
      P(arguments, 1)
      delete this.f[String(a)]
    }
    k.entries = function b() {
      var e = this,
        c,
        d,
        f,
        g,
        h,
        q
      return M(b, function(b) {
        switch (b.b) {
          case 1:
            ;(c = e.f), (f = new G(c))
          case 2:
            var t
            a: {
              for (t = f; 0 < t.l.length; ) {
                var w = t.l.pop()
                if (w in t.A) {
                  t = w
                  break a
                }
              }
              t = null
            }
            if (null == (d = t)) {
              b.b = 0
              break
            }
            g = x(c[d])
            h = g.next()
          case 5:
            if (h.done) {
              b.b = 2
              break
            }
            q = h.value
            return F(b, [d, Q(q)], 6)
          case 6:
            ;(h = g.next()), (b.b = 5)
        }
      })
    }
    k.forEach = function(b, e) {
      P(arguments, 1)
      for (var c = x(this), d = c.next(); !d.done; d = c.next()) {
        var f = x(d.value)
        d = f.next().value
        f = f.next().value
        b.call(e, f, d, this)
      }
    }
    k.get = function(b) {
      P(arguments, 1)
      var e = this.f
      b = String(b)
      return e[b] ? Q(e[b][0]) : null
    }
    k.getAll = function(b) {
      P(arguments, 1)
      return (this.f[String(b)] || []).map(Q)
    }
    k.has = function(b) {
      P(arguments, 1)
      return String(b) in this.f
    }
    k.keys = function e() {
      var c = this,
        d,
        f,
        g,
        h,
        q
      return M(e, function(e) {
        1 == e.b && ((d = x(c)), (f = d.next()))
        if (3 != e.b) {
          if (f.done) {
            e.b = 0
            return
          }
          g = f.value
          h = x(g)
          q = h.next().value
          return F(e, q, 3)
        }
        f = d.next()
        e.b = 2
      })
    }
    k.set = function(e, c, d) {
      P(arguments, 2)
      var f = O.apply(null, arguments)
      this.f[f[0]] = [[f[1], f[2]]]
    }
    k.values = function c() {
      var d = this,
        f,
        g,
        h,
        q,
        w
      return M(c, function(c) {
        1 == c.b && ((f = x(d)), (g = f.next()))
        if (3 != c.b) {
          if (g.done) {
            c.b = 0
            return
          }
          h = g.value
          q = x(h)
          q.next()
          w = q.next().value
          return F(c, w, 3)
        }
        g = f.next()
        c.b = 2
      })
    }
    X.prototype._asNative = function() {
      for (var c = new S(), d = x(this), f = d.next(); !f.done; f = d.next()) {
        var g = x(f.value)
        f = g.next().value
        g = g.next().value
        c.append(f, g)
      }
      return c
    }
    X.prototype._blob = function() {
      for (
        var c = '----formdata-polyfill-' + Math.random(),
          d = [],
          f = x(this),
          g = f.next();
        !g.done;
        g = f.next()
      ) {
        var h = x(g.value)
        g = h.next().value
        h = h.next().value
        d.push('--' + c + '\r\n')
        h instanceof Blob
          ? d.push(
              'Content-Disposition: form-data; name="' +
                g +
                '"; filename="' +
                h.name +
                '"\r\n',
              'Content-Type: ' +
                (h.type || 'application/octet-stream') +
                '\r\n\r\n',
              h,
              '\r\n'
            )
          : d.push(
              'Content-Disposition: form-data; name="' +
                g +
                '"\r\n\r\n' +
                h +
                '\r\n'
            )
      }
      d.push('--' + c + '--')
      return new Blob(d, { type: 'multipart/form-data; boundary=' + c })
    }
    X.prototype[Symbol.iterator] = function() {
      return this.entries()
    }
    X.prototype.toString = function() {
      return '[object FormData]'
    }
    W && (X.prototype[W] = 'FormData')
    T &&
      (R.XMLHttpRequest.prototype.send = function(c) {
        c instanceof X
          ? ((c = c._blob()),
            this.setRequestHeader('Content-Type', c.type),
            T.call(this, c))
          : T.call(this, c)
      })
    if (U) {
      var Y = R.fetch
      R.fetch = function(c, d) {
        d && d.body && d.body instanceof X && (d.body = d.body._blob())
        return Y.call(this, c, d)
      }
    }
    V &&
      (R.navigator.sendBeacon = function(c, d) {
        d instanceof X && (d = d._asNative())
        return V.call(this, c, d)
      })
    R.FormData = X
  }
})()
