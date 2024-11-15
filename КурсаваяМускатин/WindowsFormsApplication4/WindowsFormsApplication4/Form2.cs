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
    public partial class Form2 : Form
    {

        BindingList<ReisObject> reisObjects = new BindingList<ReisObject>();

        public Form2()
        {
            InitializeComponent();
            
        }

       
        
        private void button1_Click(object sender, EventArgs e)
        {
            {
                var NovArr = new string[100]; 
                var reis = new Global.Reis[100];
                
                if (File.Exists(Global.fileName))
                {

                    int R = Global.NameArr(out NovArr, out reis);
                    if (textBox1.Text == "" || textBox2.Text == "" || textBox3.Text == "" || textBox7.Text == "" || textBox8.Text == "" || textBox4.Text == "" ||  textBox5.Text == "" || textBox6.Text == "" || (!radioButton1.Checked && !radioButton2.Checked && !radioButton3.Checked && !radioButton4.Checked && !radioButton5.Checked && !radioButton6.Checked))
                    {
                        MessageBox.Show("Не все поля заполнены!");this.DialogResult = DialogResult.None;
                        return;
                    }
                    
                    else
                    {
                        Global.file = new FileStream(Global.fileName, FileMode.Append);

                        Global.SW = new StreamWriter(Global.file);
                        string text = "";
                        if (radioButton1.Checked) text = radioButton1.Text;
                        if (radioButton2.Checked) text = radioButton2.Text;
                        if (radioButton3.Checked) text = radioButton3.Text;
                        if (radioButton4.Checked) text = radioButton4.Text;
                        if (radioButton5.Checked) text = radioButton5.Text;
                        if (radioButton6.Checked) text = radioButton6.Text;
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
                        Global.SW.Close();
                        MessageBox.Show("Запись добавлена!");
                        this.DialogResult = DialogResult.None;
                    }
                }
                else
                {
                    MessageBox.Show("Файл не найден!");
                }
            }


        }



        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsLetter(e.KeyChar) | (e.KeyChar == Convert.ToChar(" ")) | (e.KeyChar == Convert.ToChar("-")) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | Char.IsLetter(e.KeyChar) | (e.KeyChar == Convert.ToChar("-")) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox5_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | (e.KeyChar == Convert.ToChar(",")) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

       

        private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | e.KeyChar == ':' |  e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void textBox6_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (Char.IsNumber(e.KeyChar) | e.KeyChar == '\b') return;
            else
                e.Handled = true;
        }

        private void textBox3_KeyPress_1(object sender, KeyPressEventArgs e)
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
    }
}
