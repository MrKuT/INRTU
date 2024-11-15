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
   
    public partial class Form5 : Form
    {
       
        
        public Form5()
        {

            InitializeComponent();
            
        }

        

        private void Form5_Deactivate(object sender, EventArgs e)
        {
Global.SR.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void Form5_Load(object sender, EventArgs e)
        {
            {

                if (File.Exists(Global.fileName))
                {
                    string[] Array;
                    Global.Reis[] reis;
                    int R = Global.NameArr(out Array, out reis);
                    if (R == 0)
                    {
                        MessageBox.Show("Записей не найдено!");
                        Close();
                    }
                    else
                    {
                        string line;

                        Global.file = new FileStream(Global.fileName, FileMode.Open);
                        Global.SR = new StreamReader(Global.file);
                        int N = 1;
                        int i = 0;
                        while (!Global.SR.EndOfStream)
                        {

                            line = Global.SR.ReadLine();
                            string[] Arr = line.Split('|');

                            Global.City = Arr[0];
                            Global.Tup = Arr[2];
                            Global.Tupmin = Arr[7];
                            Global.Tdown = Arr[4];
                            Global.Tdownmin = Arr[8];
                            Global.Aircraft = Arr[3];
                            Global.Price = Convert.ToSingle(Arr[5]);
                            Global.Nreisa = Convert.ToInt32(Arr[1]);
                            Global.freep = Convert.ToInt32(Arr[6]);
                            dataGridView1.Rows.Add();
                            dataGridView1[0, i].Value = Convert.ToString(N);
                            dataGridView1[1, i].Value = Global.City;
                            dataGridView1[2, i].Value = Global.Tup+":"+Global.Tupmin;
                            dataGridView1[3, i].Value = Global.Tdown+":"+Global.Tdownmin;
                            dataGridView1[4, i].Value = Global.Aircraft;
                            dataGridView1[5, i].Value = Convert.ToString(Global.Price);
                            dataGridView1[6, i].Value = Convert.ToString(Global.Nreisa);
                            dataGridView1[7, i].Value = Convert.ToString(Global.freep);

                            i++; N++;

                        }
                    }
                }
                else
                {
                    MessageBox.Show("Файл не найден!");
                    Close();
                }

            }
        }
    }

   
}
