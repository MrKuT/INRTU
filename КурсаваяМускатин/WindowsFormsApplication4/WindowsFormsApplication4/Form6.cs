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
    public partial class Form6 : Form
    {
        public Form6()
        {
            InitializeComponent();
        }

        private void maskedTextBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if(Char.IsNumber(e.KeyChar) | Char.IsLetter(e.KeyChar) | (e.KeyChar == Convert.ToChar("-")) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
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
                    {
                        listBox1.Items.Clear();
                        string[] Z1Arr;
                        Global.Reis[] reis;
                        int R = Global.NameArr(out Z1Arr, out reis);
                        for (int i = 0; i < R; i++)
                        {
                            
                            if ((reis[i].City == maskedTextBox1.Text))
                            {
                                listBox1.Items.Add("Номер рейса из города"+" "+ reis[i].City+" :"+reis[i].Nreisa);
                            }
                        }
                        if (listBox1.Items.Count == 0) MessageBox.Show("Записей по запросу не найдено!");
                    }
                }
                else
                {
                    MessageBox.Show("Файл не найден!");
                }
            }

        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
