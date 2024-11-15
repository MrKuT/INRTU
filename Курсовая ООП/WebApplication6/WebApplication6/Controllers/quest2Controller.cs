using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebApplication6.Models;

namespace WebApplication6.Controllers
{
    public class quest2Controller : Controller
    {
        // GET: quest2
        private labEntities db = new labEntities();
        public ActionResult Index()
        {
            ViewBag.id_user = new SelectList(db.users, "id_users", "surname");
            return View();
        }

        public ActionResult Query2(int id_user)
        {
            var q2 = db.Запрос2(id_user);
            return View(q2);
        }
    }
}