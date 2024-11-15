using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebApplication6.Models;

namespace WebApplication6.Controllers
{
    public class quest1Controller : Controller
    {
        // GET: quest1
        private labEntities db = new labEntities();
        public ActionResult Index()
        {
            ViewBag.id_status = new SelectList(db.status_req, "id_status", "status_name");
            ViewBag.id_admin = new SelectList(db.admin, "id_admin", "surname");
            return View();
        }
        
        public ActionResult Query1(int id_admin, int id_status, DateTime time1, DateTime time2)
        {
            var q1 = db.Запрос1(id_admin, id_status, time1, time2);
            return View(q1);
        }
    }
}