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
    public class usersController : Controller
    {
        private labEntities db = new labEntities();

        // GET: users
        public ActionResult Index()
        {
            var users = db.users.Include(u => u.podr);
            return View(users.ToList());
        }

        // GET: users/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            users users = db.users.Find(id);
            if (users == null)
            {
                return HttpNotFound();
            }

            return View(users);
        }

        // GET: users/Create
        public ActionResult Create()
        {
            ViewBag.podr_id_podr = new SelectList(db.podr, "id_podr", "namePodr");
            return View();
        }

        // POST: users/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id_users,podr_id_podr,login_user,password_user,surname,name,middlename,access,phone")] users users)
        {
            int logcheck = 0;
            int phonecheck = 0;
            logcheck = (from persons in db.admin where persons.login_admin == users.login_user select persons).Count();
            logcheck += (from persons in db.users where persons.id_users != users.id_users where persons.login_user == users.login_user select persons).Count();
            phonecheck = (from persons in db.admin  where persons.phone == users.phone select persons).Count();
            phonecheck += (from persons in db.users where persons.id_users != users.id_users where persons.phone == users.phone select persons).Count();

            if (ModelState.IsValid && phonecheck == 0 && logcheck == 0)
            {
                db.users.Add(users);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            
            if (phonecheck != 0) { ModelState.AddModelError(String.Empty, "Пользователь с таким телефоном уже существует"); }
            if (logcheck != 0) { ModelState.AddModelError(String.Empty, "Пользователь с таким логином уже существует"); }

            ViewBag.podr_id_podr = new SelectList(db.podr, "id_podr", "namePodr", users.podr_id_podr);
            return View(users);
        }

        // GET: users/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            users users = db.users.Find(id);
            if (users == null)
            {
                return HttpNotFound();
            }

            ViewBag.podr_id_podr = new SelectList(db.podr, "id_podr", "namePodr", users.podr_id_podr);
            return View(users);
        }

        // POST: users/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id_users,podr_id_podr,login_user,password_user,surname,name,middlename,access,phone")] users users)
        {
            int logcheck = 0;
            int phonecheck = 0;
            logcheck = (from persons in db.admin where persons.id_admin != users.id_users where persons.login_admin == users.login_user select persons).Count();
            logcheck += (from persons in db.users where persons.id_users != users.id_users where persons.login_user == users.login_user select persons).Count();
            phonecheck = (from persons in db.admin where persons.id_admin != users.id_users where persons.phone == users.phone select persons).Count();
            phonecheck += (from persons in db.users where persons.id_users != users.id_users where persons.phone == users.phone select persons).Count();

            if (ModelState.IsValid && phonecheck == 0 && logcheck == 0)
            {
                db.Entry(users).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            if (phonecheck != 0) { ModelState.AddModelError(String.Empty, "Пользователь с таким телефоном уже существует"); }
            if (logcheck != 0) { ModelState.AddModelError(String.Empty, "Пользователь с таким логином уже существует"); }

            ViewBag.podr_id_podr = new SelectList(db.podr, "id_podr", "namePodr", users.podr_id_podr);
            return View(users);
        }

        // GET: users/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            users users = db.users.Find(id);
            if (users == null)
            {
                return HttpNotFound();
            }
            return View(users);
        }

        // POST: users/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {   
            int usersreq = 0;
            usersreq = (from req in db.requests where req.users_id_users == id select req).Count();
            users users = db.users.Find(id);
            if (usersreq == 0)
            {
                db.users.Remove(users);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            if (usersreq != 0) { ModelState.AddModelError(String.Empty, $"Уданного пользователя обнаружены заявки в количестве {usersreq}. Для удаления пользователя удалите его записи"); }


            return View(users);
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
