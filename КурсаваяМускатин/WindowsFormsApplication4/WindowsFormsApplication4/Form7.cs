using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApplication4
{
    public partial class Form7 : Form
    {
        public Form7()
        {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            {
                if (File.Exists(Global.fileName))
                {
                    if (maskedTextBox1.Text == "")
                    {
                        MessageBox.Show("Введите название города!");
                    }
                    else
                    {int FP = 0;
                        listBox1.Items.Clear();
                        string[] Z2Arr;
                        Global.Reis[] reis;
                        int R = Global.NameArr(out Z2Arr, out reis);
                        for (int i = 0; i < R; i++)
                        {
                            
                            if ((reis[i].City == maskedTextBox1.Text))
                            {
                                FP = FP+Convert.ToInt32(reis[i].freep);}
                                
                            
                        } listBox1.Items.Add("На рейсе(ах) из города " + maskedTextBox1.Text + ". Кол-во свободных мест " + Convert.ToString(FP));
                           
                        if (listBox1.Items.Count == 0) MessageBox.Show("Записей по запросу не найдено!");
                    }
                }
                else
                {
                    MessageBox.Show("Файл не найден!");
                }
            }

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
