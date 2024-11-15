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
   
    public partial class Form4 : Form
    {
        public Form4()
        {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (File.Exists(Global.fileName))
            {
                if (maskedTextBox1.Text == "")
                {
                    MessageBox.Show("Введите номер записи!");
                }
                else
                {
                    string[] DelArr;
                    Global.Reis[] reis;
                    int R = Global.NameArr(out DelArr, out reis);
                    int N = Convert.ToInt32(maskedTextBox1.Text);
                    if (N > 0 && N - 1 < R)
                    {
                        Global.file = new FileStream(Global.fileName, FileMode.Create);
                        Global.SW = new StreamWriter(Global.file);
                        for (int i = 0; i < R; i++)
                        {
                            if (i != N - 1)
                                Global.SW.WriteLine(DelArr[i]); 
                        }
                        Global.SW.Close();
                        MessageBox.Show("Запись удалена!");
                    }
                    else
                    {
                        MessageBox.Show("Запись не найдена!");
                    }
                }
            }
            else
            {
                MessageBox.Show("Файл не найден!");
            }
        }

        private void maskedTextBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }
    }

    
    
}
