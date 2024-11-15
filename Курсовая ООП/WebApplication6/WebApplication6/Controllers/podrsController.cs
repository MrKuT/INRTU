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
    public class podrsController : Controller
    {
        private labEntities db = new labEntities();

        // GET: podrs
        public ActionResult Index()
        {
            return View(db.podr.ToList());
        }

        // GET: podrs/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            podr podr = db.podr.Find(id);
            if (podr == null)
            {
                return HttpNotFound();
            }
            return View(podr);
        }

        // GET: podrs/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: podrs/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id_podr,namePodr")] podr podr)
        {
            if (ModelState.IsValid)
            {
                db.podr.Add(podr);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(podr);
        }

        // GET: podrs/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            podr podr = db.podr.Find(id);
            if (podr == null)
            {
                return HttpNotFound();
            }
            return View(podr);
        }

        // POST: podrs/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id_podr,namePodr")] podr podr)
        {
            if (ModelState.IsValid)
            {
                db.Entry(podr).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(podr);
        }

        // GET: podrs/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            podr podr = db.podr.Find(id);
            if (podr == null)
            {
                return HttpNotFound();
            }
            return View(podr);
        }

        // POST: podrs/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            int userspodr = 0;
            userspodr = (from persons in db.users where persons.podr_id_podr == id select persons).Count();
            podr podr = db.podr.Find(id);
            if ( userspodr == 0 )
            {
                
                db.podr.Remove(podr);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            if (userspodr != 0) { ModelState.AddModelError(String.Empty, $"К данному подразделению относятся пользователи в количестве {userspodr}. Для удаления в подразделении не должно быть пользователей"); }
            

            return View(podr);
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
