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
    public class adminsController : Controller
    {
        private labEntities db = new labEntities();

        // GET: admins
        public ActionResult Index()
        {
            return View(db.admin.ToList());
        }

        // GET: admins/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            admin admin = db.admin.Find(id);
            if (admin == null)
            {
                return HttpNotFound();
            }
            return View(admin);
        }

        // GET: admins/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: admins/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id_admin,login_admin,password_admin,surname,name,middlename,phone")] admin admin)
        {
            int logcheck = 0;
            int phonecheck = 0;
            logcheck = (from persons in db.admin where persons.id_admin != admin.id_admin where persons.login_admin == admin.login_admin select persons).Count();
            logcheck += (from persons in db.users where persons.login_user == admin.login_admin select persons).Count();
            phonecheck = (from persons in db.admin where persons.id_admin != admin.id_admin where persons.phone == admin.phone select persons).Count();
            phonecheck += (from persons in db.users where persons.phone == admin.login_admin select persons).Count();

            if (ModelState.IsValid && phonecheck == 0 && logcheck == 0)
            {
                db.admin.Add(admin);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            if (phonecheck != 0) { ModelState.AddModelError(String.Empty,"Пользователь с таким телефоном уже существует"); }
            if (logcheck != 0) { ModelState.AddModelError(String.Empty, "Пользователь с таким логином уже существует"); }
            return View(admin);
        }

        // GET: admins/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            admin admin = db.admin.Find(id);
            if (admin == null)
            {
                return HttpNotFound();
            }
            return View(admin);
        }

        // POST: admins/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id_admin,login_admin,password_admin,surname,name,middlename,phone")] admin admin)
        {
            int logcheck = 0;
            int phonecheck = 0;
            logcheck = (from persons in db.admin where persons.id_admin != admin.id_admin where persons.login_admin == admin.login_admin select persons).Count();
            logcheck += (from persons in db.users where persons.login_user == admin.login_admin select persons).Count();
            phonecheck = (from persons in db.admin where persons.id_admin != admin.id_admin where persons.phone == admin.phone select persons).Count();
            phonecheck += (from persons in db.users where persons.phone == admin.login_admin select persons).Count();

            if (ModelState.IsValid && phonecheck == 0 && logcheck == 0)
            {
                db.Entry(admin).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            if (phonecheck != 0) { ModelState.AddModelError(String.Empty, "Пользователь с таким телефоном уже существует"); }
            if (logcheck != 0) { ModelState.AddModelError(String.Empty, "Пользователь с таким логином уже существует"); }
            return View(admin);
        }

        // GET: admins/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            admin admin = db.admin.Find(id);
            if (admin == null)
            {
                return HttpNotFound();
            }
            return View(admin);
        }

        // POST: admins/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            int usersreq = 0;
            usersreq = (from req in db.requests where req.admin_id_Admin == id select req).Count();
            admin admin = db.admin.Find(id);
            if (usersreq == 0)
            {
                db.admin.Remove(admin);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            if (usersreq != 0) { ModelState.AddModelError(String.Empty, $"Уданного администратора обнаружены закрепленные заявки в количестве {usersreq}. Для удаления администратора удалите его записи"); }
            
            return View(admin);
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
