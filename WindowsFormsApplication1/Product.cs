using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication1
{
    public class Product
    {
        public int ID_stock { get; set; }
        public string name { get; set; }
        public double priceForOne { get; set; }
        public Product(int ID_stock, string name, double priceForOne)
        {
            this.ID_stock = ID_stock;
            this.name = name;
            this.priceForOne = priceForOne;
        }
        public int getID() { return ID_stock; }
        public string getName() { return name; }
        public double getPriceForOne() { return priceForOne; }
        public string Info()

        {
            return "Склад: " + ID_stock + "  Название товара: " + name + "  Стоимость единицы: " + Convert.ToString(priceForOne);
        }
    }
}
