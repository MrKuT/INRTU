using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication1
{
    public class Edition
    {
        public string Индекс { get; set; }
        public string Название { get; set; }
        public double Подписка { get; set; }
        public int Месяцев { get; set; }
        public string Get_name() { return Название; }
        
        public Edition(string index, string name, double subscription, int month)
        {
            this.Индекс = index;
            this.Название = name;
            this.Подписка = subscription;
            this.Месяцев = month;
        }
        public string price()
        {
            return "Стоимость подписки на издание " + Название + " за " + Месяцев + " месяцев: " + (Подписка * Месяцев) + " руб.";
        }


    }
}
