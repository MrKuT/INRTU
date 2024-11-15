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
    public class requestsController : Controller
    {
        private labEntities db = new labEntities();

        // GET: requests
        public ActionResult Index()
        {
            var requests = db.requests.Include(r => r.admin).Include(r => r.status_req).Include(r => r.users).OrderByDescending(r=>r.time);
            return View(requests.ToList());
        }

        // GET: requests/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            requests requests = db.requests.Find(id);
            if (requests == null)
            {
                return HttpNotFound();
            }
            return View(requests);
        }

        // GET: requests/Create
        public ActionResult Create()
        {
            var model = new requests { time = DateTime.Now };
            ViewBag.admin_id_Admin = new SelectList(db.admin, "id_admin", "surname");
            ViewBag.status_req_id_status = new SelectList(db.status_req, "id_status", "status_name");
            ViewBag.users_id_users = new SelectList(db.users, "id_users", "surname");
            return View(model);
        }

        // POST: requests/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id_requests,users_id_users,admin_id_Admin,status_req_id_status,requests1,time,comment_req")] requests requests)
        {
            
            if (ModelState.IsValid)
            {
                requests.time = DateTime.Now;
                db.requests.Add(requests);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.admin_id_Admin = new SelectList(db.admin, "id_admin", "surname", requests.admin_id_Admin);
            ViewBag.status_req_id_status = new SelectList(db.status_req, "id_status", "status_name", requests.status_req_id_status);
            ViewBag.users_id_users = new SelectList(db.users, "id_users", "surname", requests.users_id_users);
            return View(requests);
        }

        // GET: requests/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            requests requests = db.requests.Find(id);
            if (requests == null)
            {
                return HttpNotFound();
            }
            ViewBag.admin_id_Admin = new SelectList(db.admin, "id_admin", "surname", requests.admin_id_Admin);
            ViewBag.status_req_id_status = new SelectList(db.status_req, "id_status", "status_name", requests.status_req_id_status);
            ViewBag.users_id_users = new SelectList(db.users, "id_users", "surname", requests.users_id_users);
            return View(requests);
        }

        // POST: requests/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id_requests,users_id_users,admin_id_Admin,status_req_id_status,requests1,time,comment_req")] requests requests)
        {
            if (ModelState.IsValid)
            {
                db.Entry(requests).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.admin_id_Admin = new SelectList(db.admin, "id_admin", "surname", requests.admin_id_Admin);
            ViewBag.status_req_id_status = new SelectList(db.status_req, "id_status", "status_name", requests.status_req_id_status);
            ViewBag.users_id_users = new SelectList(db.users, "id_users", "surname", requests.users_id_users);
            return View(requests);
        }

        // GET: requests/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            requests requests = db.requests.Find(id);
            if (requests == null)
            {
                return HttpNotFound();
            }
            return View(requests);
        }

        // POST: requests/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            requests requests = db.requests.Find(id);
            db.requests.Remove(requests);
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
