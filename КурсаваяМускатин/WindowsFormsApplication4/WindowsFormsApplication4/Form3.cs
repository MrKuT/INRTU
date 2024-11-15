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
    public partial class Form3 : Form
    {
        
        public Form3()
        {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            {
                var RArr = new string[100];
                var reis = new Global.Reis[100];
                if (File.Exists(Global.fileName))
                {
                    if (maskedTextBox1.Text == "")
                    {
                        MessageBox.Show("Введите номер записи!");
                    }
                    else
                    {
                        int N = Convert.ToInt32(maskedTextBox1.Text);
                        int R = Global.NameArr(out RArr, out reis);
                        if (N > 0 && (N - 1) < R)
                        {

                            textBox1.Enabled = true;
                            textBox2.Enabled = true;
                            textBox3.Enabled = true;
                            textBox4.Enabled = true;
                            textBox5.Enabled = true;
                            textBox6.Enabled = true;
                            textBox7.Enabled = true;
                            textBox8.Enabled = true;
                            radioButton1.Enabled = true;
                            radioButton2.Enabled = true;
                            radioButton3.Enabled = true;
                            radioButton4.Enabled = true;
                            radioButton5.Enabled = true;
                            radioButton6.Enabled = true;
                            button1.Enabled = true; button2.Enabled = true;
                            textBox1.Text = reis[N - 1].City;
                            textBox2.Text = reis[N - 1].Nreisa;
                            textBox3.Text = reis[N - 1].Tup;
                            textBox7.Text = reis[N - 1].Tupmin;
                            textBox4.Text = reis[N - 1].Tdown;
                            textBox8.Text = reis[N - 1].Tdownmin;
                            textBox5.Text = Convert.ToString(reis[N - 1].Price);
                            textBox6.Text = Convert.ToString(reis[N - 1].freep);
                            if (reis[N - 1].Aircraft == "Ту-134") radioButton1.Checked = true; 
                            if (reis[N - 1].Aircraft == "Ту-154") radioButton2.Checked = true;
                            if (reis[N - 1].Aircraft == "SSJ 100") radioButton3.Checked = true;
                            if (reis[N - 1].Aircraft == "Airbus A310") radioButton4.Checked = true;
                            if (reis[N - 1].Aircraft == "Airbus A330") radioButton5.Checked = true;
                            if (reis[N - 1].Aircraft == "Boeing-747") radioButton6.Checked = true;
                          }
                        else
                        {
                            MessageBox.Show("Запись не найдена!");
                            textBox1.Enabled = false;
                            textBox2.Enabled = false;
                            textBox3.Enabled = false;
                            textBox4.Enabled = false;
                            textBox5.Enabled = false;
                            textBox6.Enabled = false;
                            textBox7.Enabled = false;
                            textBox8.Enabled = false;
                            radioButton1.Enabled = false;
                            radioButton2.Enabled = false;
                            radioButton3.Enabled = false;
                            radioButton4.Enabled = false;
                            radioButton5.Enabled = false;
                            radioButton6.Enabled = false;
                            
                            button1.Enabled = false;
                        }
                    }
                }
                else
                {
                    MessageBox.Show("Файл не найден!");
                }
            }

        }

        private void Form3_Activated(object sender, EventArgs e)
        {
            textBox1.Enabled = false;
            textBox2.Enabled = false;
            textBox3.Enabled = false;
            textBox4.Enabled = false;
            textBox5.Enabled = false;
            textBox6.Enabled = false;
            textBox7.Enabled = false;
            textBox8.Enabled = false;
            radioButton1.Enabled = false;
            radioButton2.Enabled = false;
            radioButton3.Enabled = false;
            radioButton4.Enabled = false;
            radioButton5.Enabled = false;
            radioButton6.Enabled = false;

            button1.Enabled = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            var RArr = new string[100];
            var reis = new Global.Reis[100];
            if (textBox1.Text == "" || textBox2.Text == "" || textBox3.Text == "" || textBox4.Text == "" || textBox5.Text == "" || textBox6.Text == "" || (!radioButton1.Checked && !radioButton2.Checked && !radioButton3.Checked && !radioButton4.Checked && !radioButton5.Checked && !radioButton6.Checked))
            {
                MessageBox.Show("Не все поля заполнены!"); this.DialogResult = DialogResult.None;
                return;
            }
            else
            {
                int N = Convert.ToInt32(maskedTextBox1.Text);
                int R = Global.NameArr(out RArr, out reis);
                Global.file = new FileStream(Global.fileName, FileMode.Create);
                Global.SW = new StreamWriter(Global.file);
                string text = "";
                if (radioButton1.Checked) text = radioButton1.Text;
                if (radioButton2.Checked) text = radioButton2.Text;
                if (radioButton3.Checked) text = radioButton3.Text;
                if (radioButton4.Checked) text = radioButton4.Text;
                if (radioButton5.Checked) text = radioButton5.Text;
                if (radioButton6.Checked) text = radioButton6.Text;
                for (int i = 0; i < R; i++)
                {
                    if (i == N - 1)
                    {
                        Global.SW.WriteLine("{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}",
                           textBox1.Text,//город 
                           textBox2.Text, //номер рейса
                           textBox3.Text,//время вылета (час)
                           text, //самолет
                           textBox4.Text, //время посадки (час)
                           textBox5.Text, //цена
                           textBox6.Text,//свободные места
                           textBox7.Text,//время вылета (мин)
                           textBox8.Text); //время посадки (мин)
                    }
                    else
                    {
                        Global.SW.WriteLine(RArr[i]);// запись без изменений
                    }
                }
                Global.SW.Close();
                MessageBox.Show("Запись изменена!");
            }
        }

        private void maskedTextBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            
            if (Char.IsNumber(e.KeyChar) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | Char.IsLetter(e.KeyChar) | (e.KeyChar == Convert.ToChar("-")) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsLetter(e.KeyChar) | (e.KeyChar == Convert.ToChar(" ")) | (e.KeyChar == Convert.ToChar("-")) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox3_KeyPress(object sender, KeyPressEventArgs e)
        {
            textBox3.MaxLength = 2;
            if (Char.IsNumber(e.KeyChar) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox3_KeyUp(object sender, KeyEventArgs e)
        {
            TextBox temp = sender as TextBox;
            if (temp.Text.Length > 0)
                if (System.Convert.ToInt32(temp.Text) > 23)
                    temp.Text = "23";
        }

        private void textBox7_KeyPress(object sender, KeyPressEventArgs e)
        {
            textBox3.MaxLength = 2;
            if (Char.IsNumber(e.KeyChar) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox7_KeyUp(object sender, KeyEventArgs e)
        {
            TextBox temp = sender as TextBox;
            if (temp.Text.Length > 0)
                if (System.Convert.ToInt32(temp.Text) > 59)
                    temp.Text = "59";
        }

        private void textBox6_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox5_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | (e.KeyChar == Convert.ToChar(",")) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }
    }
}
