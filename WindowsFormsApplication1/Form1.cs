using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows.Forms;
using WindowsFormsApplication1;

namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
       
        struct matr
        {
            //------------------------------------------------------------------------//
            public static void Formula(int k, int l, int chislo, int[,] X, out int[,] Y)
            {
                Y = new int[k, k];
                int i, j;
                for (i = 0; i < k; i++)
                {
                    for (j = 0; j < k; j++)
                    {
                        Y[i, j] = l * X[i, j] * chislo;
                    }
                }
            }
            //------------------------------------------------------------------------//
            public static int K(int k, int[,] X,int a)
            {
                int k0 = 0;
                int i, j;                              
                for (i = 0; i < k; i++)
                {
                    for (j = 0; j < k; j++)
                        if ((i + j > k - 1) && (X[i, j] < a))
                        {
                            k0++;
                        }
                }
                return k0;
            }
            //------------------------------------------------------------------------//
            public static void Summatr(int k, int[,] X, int[,] P, out int[,] G)
            {
                G = new int[k, k];
                int i, j;
                for (i = 0; i < k; i++)
                {
                    for (j = 0; j < k; j++)
                    {
                        G[i, j] = X[i, j] + P[i, j];
                    }
                }
            }
            //------------------------------------------------------------------------//

            public static void Razmatr(int k, int[,] X, int[,] P, out int[,] G)
            {
                G = new int[k, k];
                int i, j;
                for (i = 0; i < k; i++)
                {
                    for (j = 0; j < k; j++)
                    {
                        G[i, j] = X[i, j] - P[i, j];
                    }


                }
            }          
        }       

        class Global
        {            
            public static int[,] R;
            public static int[,] T = new int[m, m];
            public static int[,] C = new int[m, m];
            public static int[,] F = new int[m, m];
            public static int[,] H = new int[m, m];
            public static int[,] G = new int[m, m];
            public static int[,] Y = new int[m, m];
            public static int m,a,b;
        };

        //=========================================

       

        public Form1()
        {
            InitializeComponent();
            dataGridView1.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
            dataGridView2.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
            dataGridView3.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
            
            
            this.dataGridView1.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.dataGridView_Change);
            this.dataGridView2.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.dataGridView_Change);
            this.dataGridView3.RowsAdded += new System.Windows.Forms.DataGridViewRowsAddedEventHandler(this.dataGridView_Change);
            
        }
       
        private void dataGridView_Change(object sender, DataGridViewRowsAddedEventArgs e)
    {
        // если в таблицу добавлена новая строка, то изменить высоту и ширину таблицы
        ChangeHeight();
        ChangeWidth();
    }
    private void ChangeWidth()
    {
        // меняем высоту таблицу по ширине всех строк
        dataGridView1.Width = dataGridView1.Columns.GetColumnsWidth(DataGridViewElementStates.Visible) +
                           dataGridView1.ColumnHeadersHeight;
        dataGridView2.Width = dataGridView2.Columns.GetColumnsWidth(DataGridViewElementStates.Visible) +
                           dataGridView2.ColumnHeadersHeight;
            dataGridView3.Width = dataGridView3.Columns.GetColumnsWidth(DataGridViewElementStates.Visible) +
                               dataGridView3.ColumnHeadersHeight;
        }
    private void ChangeHeight()
    {
        // меняем высоту таблицу по высоте всех строк
        dataGridView1.Height = dataGridView1.Rows.GetRowsHeight(DataGridViewElementStates.Visible) +
                           dataGridView1.ColumnHeadersHeight;
        dataGridView2.Height = dataGridView2.Rows.GetRowsHeight(DataGridViewElementStates.Visible) +
                           dataGridView2.ColumnHeadersHeight;
        dataGridView3.Height = dataGridView3.Rows.GetRowsHeight(DataGridViewElementStates.Visible) +
                           dataGridView3.ColumnHeadersHeight;

    }

        private void button1_Click(object sender, EventArgs e)
        {
            int i;
            if (Convert.ToInt32(maskedTextBox1.Text) < 2 || Convert.ToInt32(maskedTextBox1.Text) > 100)
            {
                MessageBox.Show("Данное значение должно быть в пределах от 2 до 100");
            }
            else
            {
                dataGridView1.Rows.Clear();
                dataGridView2.Rows.Clear();
                dataGridView3.Rows.Clear();

                dataGridView1.Columns.Clear();
                dataGridView2.Columns.Clear();
                dataGridView3.Columns.Clear();
                Global.m = Convert.ToInt32(this.maskedTextBox1.Text);

                Global.R = new int[Global.m, Global.m];

                for (i = 0; i < Global.m; i++)
                {
                    dataGridView1.Columns.Add("c" + Convert.ToString(i), "");
                    DataGridViewColumn column = dataGridView1.Columns[i];
                    column.Width = 20;
                }
                for (i = 0; i < Global.m; i++)
                {
                    dataGridView2.Columns.Add("c" + Convert.ToString(i), "");
                    DataGridViewColumn column = dataGridView2.Columns[i];
                    column.Width = 20;
                }
                for (i = 0; i < Global.m; i++)
                {
                    dataGridView3.Columns.Add("c" + Convert.ToString(i), "");
                    DataGridViewColumn column = dataGridView3.Columns[i];
                    column.Width = 30;
                }

                for (i = 0; i < Global.m; i++)
                    dataGridView1.Rows.Add();
                for (i = 0; i < Global.m; i++)
                    dataGridView2.Rows.Add();
                for (i = 0; i < Global.m; i++)
                    dataGridView3.Rows.Add();
                dataGridView1.AllowUserToAddRows = false;
                dataGridView2.AllowUserToAddRows = false;
                dataGridView3.AllowUserToAddRows = false;

                if ((dataGridView1.Location.X + dataGridView1.Width + dataGridView2.Width + 20)<this.Width) 
                {
                    dataGridView2.Location = new Point(dataGridView1.Location.X + dataGridView2.Width + 10, dataGridView1.Location.Y);
                    dataGridView3.Location = new Point(dataGridView2.Location.X + dataGridView3.Width + 10, dataGridView1.Location.Y);
                }
                if ((dataGridView1.Location.X + dataGridView1.Width + dataGridView2.Width + 20 + dataGridView3.Width) > this.Width)
                {
                    dataGridView2.Location = new Point(dataGridView1.Location.X + dataGridView2.Width + 10, dataGridView1.Location.Y);                    
                    dataGridView3.Location = new Point(dataGridView1.Location.X, dataGridView1.Location.Y + dataGridView3.Height + 30 + label2.Height);
                }
                if ((dataGridView1.Location.X + dataGridView1.Width + dataGridView2.Width + 20) > this.Width)
                {
                    dataGridView2.Location = new Point(dataGridView1.Location.X, dataGridView1.Location.Y + dataGridView2.Height + 30 + label4.Height);
                    dataGridView3.Location = new Point(dataGridView2.Location.X, dataGridView2.Location.Y + dataGridView3.Height + 30 + label2.Height);
                }
                label4.Location = new Point(dataGridView2.Location.X, dataGridView2.Location.Y - label4.Height - 3);
                label2.Location = new Point(dataGridView3.Location.X, dataGridView3.Location.Y - label2.Height - 3);
            }
        }

        private void выходToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void подсчетМатрицыToolStripMenuItem_Click(object sender, EventArgs e)
        {
            int [,] T = new int[Global.m, Global.m];
            int[,] C = new int[Global.m, Global.m];
            int i, j;
            for (i = 0; i < Global.m; i++)
            {

                for (j = 0; j < Global.m; j++)
                {
                    T[i, j] = Convert.ToInt32(dataGridView2[j, i].Value);
                    C[i, j] = Convert.ToInt32(dataGridView1[j, i].Value);
                }
            }
            int k1 = matr.K(Global.m,C,Global.a);
            int k2 = matr.K(Global.m, T, Global.b);            
            if (k1 < k2)
            {

                matr.Formula(Global.m, 2, k1, C, out Global.Y);
                matr.Summatr(Global.m, T, Global.Y, out Global.F);
                for (i = 0; i < Global.m; i++)
                    for (j = 0; j < Global.m; j++)
                        this.dataGridView3[j, i].Value = Convert.ToString(Global.F[i, j]);
            }
            else
            {
               
                matr.Formula(Global.m, 1, k2, T, out Global.Y);
                matr.Formula(Global.m, 2, 1, C, out Global.G);
                matr.Razmatr(Global.m, Global.G, Global.Y, out Global.H);
                for (i = 0; i < Global.m; i++)
                    for (j = 0; j < Global.m; j++)
                        this.dataGridView3[j, i].Value = Convert.ToString(Global.H[i, j]);
            };

            string f1 = k1.ToString();
            string f2 = k2.ToString();
            label8.Text = f1;
            label9.Text = f2;
            dataGridView3.Width = dataGridView3.Columns.GetColumnsWidth(DataGridViewElementStates.Visible) +
                               dataGridView3.ColumnHeadersHeight;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Global.a = Convert.ToInt32(maskedTextBox2.Text);
            Global.b = Convert.ToInt32(maskedTextBox3.Text);
        }      

        //=======================================================================================
        private void DataGridView1_CellValidating(object sender, DataGridViewCellValidatingEventArgs e)
        {
            if (Global.m == Convert.ToUInt32(maskedTextBox1.Text))
            {
                const string disallowed = @"[^0-9-,]";
                var newText = Regex.Replace(e.FormattedValue.ToString(), disallowed, string.Empty);
                dataGridView1.Rows[e.RowIndex].ErrorText = "";
                if (dataGridView1.Rows[e.RowIndex].IsNewRow) return;
                if (string.CompareOrdinal(e.FormattedValue.ToString(), newText) == 0) return;
                e.Cancel = true;
                MessageBox.Show("Можно вводить только числовые значения");
            }
        }

        //=======================================================================================
        private void button4_Click(object sender, EventArgs e) // Рандомные числа
        {
            int textMin = 1, textMax = 20;

            Random rnd = new Random();

            for (int x = 0; x < dataGridView1.ColumnCount; x++)
            {
                for (int y = 0; y < dataGridView1.RowCount; y++)
                {
                    dataGridView1[x, y].Value = rnd.Next(Convert.ToInt32(textMin), Convert.ToInt32(textMax)); // Значения берутся рандомно из заданного пользователем интервала
                    dataGridView2[x, y].Value = rnd.Next(Convert.ToInt32(textMin), Convert.ToInt32(textMax)); // Значения берутся рандомно из заданного пользователем интервала
                }
            }
        }

        //==========================================  2 Lab  ====================================================
        //==================================================================================
        Vector v1 = new Vector(0, 0, 0, 0);
        Vector v2 = new Vector(0, 0, 0, 0);
        Vector v = new Vector(0, 0, 0, 0);
        private void button5_Click(object sender, EventArgs e)
        {               
            v = v1 + v2;
            label18.Text = Convert.ToString(v.x1 + v.x2);
            label19.Text = Convert.ToString(v.y1 + v.y2);
            Graphics g = pictureBox1.CreateGraphics();
            g.TranslateTransform((float)pictureBox1.Width / 2, (float)pictureBox1.Height / 2);
            g.Clear(Color.White);

            g.DrawLine(new Pen(Brushes.Black, 2), new Point(-500, 0), new Point(500, 0));
            g.DrawLine(new Pen(Brushes.Black, 2), new Point(0, -500), new Point(0, 500));

            g.DrawLine(new Pen(Brushes.Blue, 5), new Point(v1.x1, -v1.y1), new Point(v1.x2, -v1.y2));//AB
            g.DrawLine(new Pen(Brushes.Yellow, 5), new Point(v2.x1, -v2.y1), new Point(v2.x2, -v2.y2));//CD
            if (v1.x1 == v2.x1 & v1.y1 == v2.y1)
            {
                g.DrawLine(new Pen(Brushes.Green, 5), new Point(v.x1, -v.y1), new Point(v.x2, -v.y2));
            }
           
                  
        }
        private void button10_Click(object sender, EventArgs e)
        {
            v = v1 - v2;
            label18.Text = Convert.ToString(v.x1-v.x2);
            label19.Text = Convert.ToString(v.y1-v.y2);
            Graphics g = pictureBox1.CreateGraphics();
            g.TranslateTransform((float)pictureBox1.Width / 2, (float)pictureBox1.Height / 2);
            g.Clear(Color.White);

            g.DrawLine(new Pen(Brushes.Black, 2), new Point(-500, 0), new Point(500, 0));
            g.DrawLine(new Pen(Brushes.Black, 2), new Point(0, -500), new Point(0, 500));

            g.DrawLine(new Pen(Brushes.Blue, 5), new Point(v1.x1, -v1.y1), new Point(v1.x2, -v1.y2));//AB
            g.DrawLine(new Pen(Brushes.Yellow, 5), new Point(v1.x2, -v1.y2), new Point(v2.x1 + v2.x2, -v2.y2 + -v2.y1));//CD 
            if (v1.x1 == v2.x1 & v1.y1 == v2.y1)
            {
                g.DrawLine(new Pen(Brushes.Green, 5), new Point(v1.x1, -v1.y1), new Point(v2.x1 + v2.x2, -v2.y2 + -v2.y1));
            }

        }
        
        //==================================================================================
        private void button7_Click(object sender, EventArgs e)
        {            
            double d = v1 * v2;
            label21.Text = Convert.ToString(d);
        }
        //==================================================================================
        private void button8_Click(object sender, EventArgs e)
        {                        
            label22.Text = Convert.ToString(Vector.lenghtV(v1));
            label28.Text = Convert.ToString(Vector.lenghtV(v2));
        }
        //==================================================================================
        private void button9_Click(object sender, EventArgs e)
        {            
            label23.Text = Convert.ToString(Vector.proizv(v1,v2));
        }    
       

        //=============================================================================

        
        private void button6_Click(object sender, EventArgs e)
        {          
            Graphics g = pictureBox1.CreateGraphics();
            g.TranslateTransform((float)pictureBox1.Width / 2, (float)pictureBox1.Height / 2);
            g.Clear(Color.White);
                        
            g.DrawLine(new Pen(Brushes.Black, 2), new Point(-500, 0), new Point(500, 0));
            g.DrawLine(new Pen(Brushes.Black, 2), new Point(0, -500), new Point(0, 500));
            
            g.DrawLine(new Pen(Brushes.Blue, 5), new Point(v1.x1, -v1.y1), new Point(v1.x2, -v1.y2));//AB
            g.DrawLine(new Pen(Brushes.Yellow, 5), new Point(v2.x1, -v2.y1), new Point(v2.x2, -v2.y2));//CD

            
        }

        //==================================================================================

        private void button11_Click(object sender, EventArgs e)
        {
            int Xa, Ya, Xb, Yb, Xc, Yc, Xd, Yd, ab1, ab2, cd1, cd2;
            try{
                Xa = Convert.ToInt32(textBox5.Text);
                Ya = Convert.ToInt32(textBox6.Text);

                Xb = Convert.ToInt32(textBox9.Text);
                Yb = Convert.ToInt32(textBox10.Text);

                Xc = Convert.ToInt32(textBox7.Text);
                Yc = Convert.ToInt32(textBox8.Text);

                Xd = Convert.ToInt32(textBox11.Text);
                Yd = Convert.ToInt32(textBox12.Text);
                this.v1 = new Vector(Xa, Ya,Xb,Yb);
                this.v2 = new Vector(Xc, Yc, Xd, Yd);
                
                ab1 = v1.coordX;//вектор координаты AB{ab1;ab2}
                ab2 = v1.coordY;
                cd1 = v2.coordX;//вектор координаты CD{cd1;cd2}
                cd2 = v2.coordY;
                label29.Text = "__" + "\nAB = ";
                label14.Text = "__" + "\nCD = ";
                label29.Text += " {" + Convert.ToString(v1.coordX) + "," + Convert.ToString(v1.coordY) + "}";
                label14.Text += " {" + Convert.ToString(v2.coordX) + "," + Convert.ToString(v2.coordY) + "}";

                button5.Enabled = true; button6.Enabled = true; button7.Enabled = true; button8.Enabled = true;
                button9.Enabled = true; button10.Enabled = true;
                Graphics g = pictureBox1.CreateGraphics();
                g.TranslateTransform((float)pictureBox1.Width / 2, (float)pictureBox1.Height / 2);
                g.Clear(Color.White);
                
    }catch (FormatException) { 
                const string err = "Ошибка ввода. Введите числовые значения!";
                MessageBox.Show(err); }
        }

        //==========================================  3 Lab  ====================================================
        //==================================================================================

        public TListOfDataObjects Data = new TListOfDataObjects();


        private void button3_Click(object sender, EventArgs e)
        {
          
            if (tr != null)
            {
                MessageBox.Show("Идеальное время прохождения пути " + Convert.ToString(tr.bTime(tr.distance, tr.speed))+" ч.");
            }
            else { MessageBox.Show("Вы не выбрали транспорт!!!"); }
        }
        public int j = 0;
        public static int kolvo = 1;
        private void button12_Click(object sender, EventArgs e)
        {
            if (radioButton1.Checked) { 
            if ((load.Text.Length != 0) && (distance.Text.Length != 0))
            {
                    this.Data.Add(new Aircraft(textBox3.Text, Convert.ToInt32(distance.Text), Convert.ToInt32(speed.Text), Convert.ToInt32(range.Text), Convert.ToInt32(load.Text)));
                    

                    
                    j++;
                    MessageBox.Show("Запись добавлена");
                    textBox3.Text = "";
                    speed.Text = "";
                    distance.Text = "";
                    load.Text = "";
                    wagons.Text = "";
                    radioButton1.Checked = false;
                    radioButton2.Checked = false;
                    label25.Visible = false;
                    textBox7.Visible = false;
                    label24.Visible = false;
                    textBox6.Visible = false;
                    button12.Enabled = false;                    
                    label34.Visible = false;
                    label35.Visible = false;
                    label42.Visible = false;
                    label43.Visible = false;
                    range.Visible = false;
                    load.Visible = false;
                }
            else { MessageBox.Show("Вы забыли заполнить поля для самолета!!!"); } }
            if (radioButton2.Checked)
            {
                if ((wagons.Text.Length != 0))
                {
                    this.Data.Add(new Train(textBox3.Text, Convert.ToInt32(distance.Text), Convert.ToInt32(speed.Text),  Convert.ToInt32(wagons.Text)));
                   

                    j++;
                    MessageBox.Show("Запись добавлена");
                    textBox3.Text = "";
                    speed.Text = "";
                    distance.Text = "";
                    load.Text = "";
                    wagons.Text = "";
                    radioButton1.Checked = false;
                    radioButton2.Checked = false;
                    label25.Visible = false;
                    textBox7.Visible = false;
                    label24.Visible = false;
                    textBox6.Visible = false;
                    button12.Enabled = false;
                    wagons.Visible = false;
                    label37.Visible = false;                    
                }
                else { MessageBox.Show("Вы забыли указать кол-во вагонов!!!"); }
            }
            BindingSource binding1 = new BindingSource();
            binding1.DataSource = this.Data;

            dataGridView5.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
            dataGridView5.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.AllCells;


            this.dataGridView5.AutoGenerateColumns = true;
            this.dataGridView5.DataSource = binding1;
            this.dataGridView5.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
                        
        }

        public Transport tr;
        private void dataGridView5_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int i;
            i = dataGridView5.CurrentRow.Index;           
            tr = (Transport)Data[i];
        }
        private void radioButton1_CheckedChanged_1(object sender, EventArgs e)
        {
            label37.Visible = false;
            wagons.Visible = false;
            label34.Visible = true;
            label35.Visible = true;
            label42.Visible = true;
            label43.Visible = true;
            range.Visible = true;
            load.Visible = true;
            button12.Enabled = true;
        }

        private void radioButton2_CheckedChanged_1(object sender, EventArgs e)
        {
            label37.Visible = true;
            wagons.Visible = true;
            label34.Visible = false;
            label35.Visible = false;
            label42.Visible = false;
            label43.Visible = false;
            range.Visible = false;
            load.Visible = false;
            button12.Enabled = true;
        }

        private void speed_KeyPress(object sender, KeyPressEventArgs e)
        {
            char number = e.KeyChar;
            if (!Char.IsDigit(number) && number != 8) // цифры и клавиша BackSpace
            {
                e.Handled = true;
            }
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
           
        }
        
        private void tabControl1_Click(object sender, EventArgs e)
        {
            button12.Enabled = false;

                        
        }

        //-----------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------4 лаб---------------------------------------------------------------------

        public ListOfDataObjects DataEdit = new ListOfDataObjects();
        public List<Product> products = new List<Product>();        
        public List<Stock> stock = new List<Stock>();        
        private void button13_Click(object sender, EventArgs e)
        {
            try
            {
                List<int> mas = textBox1.Text.Split(" ".ToCharArray(), StringSplitOptions.RemoveEmptyEntries).Select(x => Convert.ToInt32(x)).ToList();               
                label45.Text = "Сумма элементов массива меньших 10: " + mas.Select(x => Convert.ToInt32(x)).Where(x => x < 10).Sum().ToString();
            }
            catch { MessageBox.Show("Невозможно распознать строку в массив || заданное значение не является int."); }

        }

        private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
        {
            textBox4.MaxLength = 1;
            char word = e.KeyChar;
            if (!Char.IsLetter(word) && word != 8) // цифры и клавиша BackSpace
            {
                e.Handled = true;
            }
            button14.Enabled = true;       
        }

        private void button14_Click(object sender, EventArgs e)
        {

            label46.Visible = true;
            List<String> Letter = textBox2.Text.Split("\r\n".ToCharArray(), StringSplitOptions.RemoveEmptyEntries).Select(x => Convert.ToString(x)).ToList();  
            List<String> s= Letter.Select(x => Convert.ToString(x)).Where(x => x.Substring(0, 1) == textBox4.Text || x.Substring(0, 1) == textBox4.Text.ToUpper() || x.Substring(0, 1) == textBox4.Text.ToLower()).ToList();
            label46.Text = "Строк начинающихся с буквы " + "\"" + textBox4.Text + "\"" + " :" + s.Count();
            textBox14.Clear();
            textBox14.Text = "Строк начинающихся с буквы " + "\"" + textBox4.Text + "\"" + " :"+ Environment.NewLine; 
            for (int i = 0; i < s.Count(); i++) { 
            textBox14.Text += s[i] + Environment.NewLine;
            }
        }

        private void tabControl1_Enter(object sender, EventArgs e)
        {
            
        }

        private void button15_Click(object sender, EventArgs e)
        {
           // double sum = 0;
            string name = textBox13.Text;            
            var edit = from n in DataEdit where n.Название.Equals(name) select n;
            foreach (Edition st in edit)
            { MessageBox.Show(st.price());  }
            //sum = sum + st.Подписка * st.Месяцев;
            //MessageBox.Show("Стоимость подписки на издание " + name +  ": " + sum + " руб.");

            /*if (ed != null)
            {
                MessageBox.Show(Edition.price(ed));
            }
            else { MessageBox.Show("Вы не выбрали издание!!!"); }*/

        }

        private void tabPage4_Click(object sender, EventArgs e)
        {

        }
        public Edition ed;
        private void dataGridView6_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int i;
            i = dataGridView6.CurrentRow.Index;
            ed = (Edition)DataEdit[i];
        }

        private void button16_Click(object sender, EventArgs e)
        {
            /*var productlD = from с in products select с.getID();

            var stocklD = from o in stock select o.getID(); ;

            var pInS = productlD.Intersect(stocklD);

            listBox1.Items.Clear();

            foreach (var item in pInS)

            { listBox1.Items.Add(item); }*/
            var stockPetrov =from p in products join s in stock on p.getID() equals s.getID() where s.nameWorker =="Петров П.П." select new{Name = p.getName(), Price = p.getPriceForOne(),Stock = p.getID(),StockWorker = s.getWorker()};

            listBox1.Items.Clear();
            listBox1.Items.Add("----------Склад Петрова-----------");
            foreach (var item in stockPetrov)                
            {
                listBox1.Items.Add("Склад: " +item.Stock+"  Товар: "+item.Name + "  Стоимость за 1 шт.: " + Convert.ToString(item.Price) + " руб.");
            }


        }

        private void textBox14_KeyPress(object sender, KeyPressEventArgs e)
        {

        }

        private void button17_Click(object sender, EventArgs e)
        {
            string filename = @"";
            using (var dialog = new SaveFileDialog())
            {
                dialog.Filter = "Текстовые файлы(*.txt)|*.txt";
                if (dialog.ShowDialog(this) == DialogResult.OK)
                {
                    filename = dialog.FileName;
                    FileStream fsser = new FileStream(filename, FileMode.Append, FileAccess.Write);
                    BinaryFormatter bfser = new BinaryFormatter();
                    bfser.Serialize(fsser, Data);
                    fsser.Close();
                }
            }
            
           
        }

        private void button18_Click(object sender, EventArgs e)
        {
            string filename = @"";
            OpenFileDialog openFileDialog1 = new OpenFileDialog() { Filter = "Текстовые файлы(*.txt)|*.txt" };
            if (openFileDialog1.ShowDialog() == DialogResult.OK) { 
            filename = openFileDialog1.FileName;
            FileStream fsdis = new FileStream(filename, FileMode.Open, FileAccess.Read, FileShare.Read);
            BinaryFormatter bfdis = new BinaryFormatter();
            Data = (TListOfDataObjects)bfdis.Deserialize(fsdis);
            fsdis.Close();

            BindingSource binding1 = new BindingSource();
            binding1.DataSource = this.Data;

            dataGridView5.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
            dataGridView5.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.AllCells;


            this.dataGridView5.AutoGenerateColumns = true;
            this.dataGridView5.DataSource = binding1;
            this.dataGridView5.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
            }
        }

        private void Form1_Activated(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
            DataEdit.Add(new Edition("1", "МИФ", 76.5, 5));
            DataEdit.Add(new Edition("2", "Эксмо — АСТ", 73.91, 8));
            DataEdit.Add(new Edition("3", "Экзамен", 61.9, 3));
            DataEdit.Add(new Edition("4", "Феникс", 67.72, 11));
            DataEdit.Add(new Edition("5", "LitRPG", 76.5, 2));

            products.Add(new Product(1, "Пила 'Дружба'", 765));
            products.Add(new Product(1, "Колун", 376));
            products.Add(new Product(2, "Мангал", 1573));
            products.Add(new Product(1, "Дрель", 983));
            products.Add(new Product(2, "Набор шампуров", 671));
            products.Add(new Product(1, "Кувалда", 782));
            products.Add(new Product(1, "Удочка", 3320));
            stock.Add(new Stock(1, "Петров П.П."));
            stock.Add(new Stock(2, "Иванов А.С."));
            stock.Add(new Stock(3, "Жениц С.Г."));


            BindingSource binding = new BindingSource();
            binding.DataSource = this.DataEdit;

            dataGridView6.DefaultCellStyle.WrapMode = DataGridViewTriState.True;
            dataGridView6.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.AllCells;


            this.dataGridView6.AutoGenerateColumns = true;
            this.dataGridView6.DataSource = binding;
            this.dataGridView6.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

        }

        private void button19_Click(object sender, EventArgs e)
        {
            var pID = from p in products select p.ID_stock;

            var sID = from s in stock  select s.ID_stock;
            var productsWithStoc = sID.Intersect(pID);

            listBox1.Items.Clear();
            listBox1.Items.Add("-----------Склады на которых есть товары---------------");
            foreach (var item in productsWithStoc)

                { listBox1.Items.Add("Склад: "+item); }
            
        }

        private void button20_Click(object sender, EventArgs e)
        {            
                listBox2.Items.Clear();
            listBox2.Items.Add("-----------------Продукты-----------------");
            foreach (var item in products)

            { listBox2.Items.Add(item.Info()); }
            listBox2.Items.Add("-------------------Склады-------------------");
            foreach (var item in stock)

            { listBox2.Items.Add(item.Info()); }
        }




        //1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0 0 0 0 0 0 -5
        /*Всем привет!
Как в предложении все слова подогнать под один формат?*/


    }
}
//========================================================================================================================
public class ListOfDataObjects : BindingList<Edition>
{

    protected override bool SupportsSearchingCore
    {
        get { return true; }
    }

    protected override int FindCore(PropertyDescriptor prop, object key)
    {
        return -1;
    }

}

    //====================================================================================================================================
    [Serializable]
public class TListOfDataObjects : BindingList<Transport>
{

    protected override bool SupportsSearchingCore
    {
        get { return true; }
    }

    protected override int FindCore(PropertyDescriptor prop, object key)
    {        
        return -1;
    }
    
}

//============================================================================================================