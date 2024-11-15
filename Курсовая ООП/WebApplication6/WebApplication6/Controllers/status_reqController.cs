using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using WebApplication6.Models;

namespace WebApplication6.Controllers
{
    public class status_reqController : Controller
    {
        private labEntities db = new labEntities();

        // GET: status_req
        public ActionResult Index()
        {
            return View(db.status_req.ToList());
        }

        // GET: status_req/Details/5
        public ActionResult Details(byte? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            status_req status_req = db.status_req.Find(id);
            if (status_req == null)
            {
                return HttpNotFound();
            }
            return View(status_req);
        }

        // GET: status_req/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: status_req/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id_status,status_name")] status_req status_req)
        {
            if (ModelState.IsValid)
            {
                db.status_req.Add(status_req);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(status_req);
        }

        // GET: status_req/Edit/5
        public ActionResult Edit(byte? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            status_req status_req = db.status_req.Find(id);
            if (status_req == null)
            {
                return HttpNotFound();
            }
            return View(status_req);
        }

        // POST: status_req/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id_status,status_name")] status_req status_req)
        {
            if (ModelState.IsValid)
            {
                db.Entry(status_req).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(status_req);
        }

        // GET: status_req/Delete/5
        public ActionResult Delete(byte? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            status_req status_req = db.status_req.Find(id);
            if (status_req == null)
            {
                return HttpNotFound();
            }
            return View(status_req);
        }

        // POST: status_req/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(byte id)
        {
            status_req status_req = db.status_req.Find(id);
            db.status_req.Remove(status_req);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
