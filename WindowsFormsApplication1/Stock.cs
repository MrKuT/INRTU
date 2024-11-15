using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication1
{
    public class Stock
    {
        public int ID_stock { get; set; }
        public string nameWorker { get; set; }
        public Stock(int ID_stock, string nameWorker)
        {
            this.ID_stock = ID_stock;
            this.nameWorker = nameWorker;
        }
        public int getID() { return ID_stock; }
        public string getWorker() { return nameWorker; }
        public string Info()

        {
            return "Склад: " + ID_stock + "  ФИО рабочего: " + nameWorker;
        }

    }
}
