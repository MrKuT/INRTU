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
    

    public partial class Form1 : Form
    {

        BindingList<ReisObject> reisObjects = new BindingList<ReisObject>();


        
        public Form1(string fileName)
        {
            
                InitializeComponent();
                if (Global.fileName != null)
                {
                    Global.fileName = fileName;
                    OpenFile();
                }
            }


        private void OpenFile()
        {
            try
            {
                textBox1.Clear();
                textBox1.Text = File.ReadAllText(Global.fileName);
            }
            catch (IOException ex)
            {
                MessageBox.Show(ex.Message, "Simple Editor",
                MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }
        private void SaveFile()
        {
            try
            { File.WriteAllText(Global.fileName, textBox1.Text); }
            catch (IOException ex)
            {
                MessageBox.Show(ex.Message, "Simple Editor",
                MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        



        private void добавитьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Form2 fr2 = new Form2();

            fr2.ShowDialog();
        }

        private void удалитьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Form4 fr4 = new Form4();

            fr4.ShowDialog();
        }

        private void изменитьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Form3 fr3 = new Form3();

            fr3.ShowDialog();
        }

        private void просмотрToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Form5 fr5= new Form5();

            fr5.ShowDialog();
        }

        private void рейсToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Form6 fr6 = new Form6();

            fr6.ShowDialog();
        }

        private void свободныеМестаToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Form7 fr7 = new Form7();

            fr7.ShowDialog();
        }

        private void открытьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            

        }

        private void создатьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            {
                {
                    if (File.Exists("Reis.txt"))
                    {
                        MessageBox.Show("Файл уже существует!");
                    }
                    else
                    {
                        Global.file = new FileStream("Reis.txt", FileMode.Create);
                        Global.fileName = "Reis.txt";
                        Global.file.Close();
                        MessageBox.Show("Файл создан!");

                    }
                }

            }

        }

        private void открытьToolStripMenuItem_Click_1(object sender, EventArgs e)
        {
            {
                if (dlgOpenFile.ShowDialog() == DialogResult.OK)
                {
                    string directory = Environment.GetFolderPath(Environment.SpecialFolder.Desktop);
                    dlgOpenFile.InitialDirectory = directory;
                    Global.fileName = dlgOpenFile.FileName;
                    OpenFile();
                }
            }

        }

        private void сохранитьToolStripMenuItem_Click(object sender, EventArgs e)
        {
            {
                if (dlgSaveFile.ShowDialog() == DialogResult.OK)
                {
                    Global.fileName = dlgSaveFile.FileName;
                    SaveFile();
                }
            }


        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Global.fileName = "Reis.txt";
        }
    }

    public class ReisObject
    {
        public string City { get; set; }
        public string Tup { get; set; }
        public string Tdown { get; set; }
        public string Aircraft { get; set; }
        public float Price { get; set; }
        public string Nreisa { get; set; }
        public int freep { get; set; }
    }

    public static class Global
    {
        public static FileStream file;
        public static StreamReader SR;
        public static StreamWriter SW;
        public static string NovArr;
        public static string City;
        public static string Tup;
        public static string Tupmin;
        public static string Tdown;
        public static string Tdownmin;
        public static string Aircraft;
        public static float Price;
        public static int Nreisa;
        public static int freep;
        public static string fileName;
        public struct Reis
        {
            public  string City;
            public  string Tup;
            public string Tupmin;
            public  string Tdown;
            public string Tdownmin;
            public  string Aircraft;
            public  double Price;
            public  string Nreisa;
            public  int freep;
        }
       
        public static int NameArr(out string[] NovArr, out Reis[] reis)
        {

            NovArr = new string[100];
            reis = new Reis[100];
            file = new FileStream(Global.fileName, FileMode.Open);
            Global.SR = new StreamReader(Global.file);
            int N = 0;// проверка кол-во строк в файле
            string[] Arr;
            while (!SR.EndOfStream)
            {
                NovArr[N] = SR.ReadLine();
                Arr = NovArr[N].Split('|');
                
                reis[N].City = Arr[0];
                reis[N].Nreisa =Arr[1];
                if (Arr[3] == "Ту-134") reis[N].Aircraft = "Ту-134";
                if (Arr[3] == "Ту-154") reis[N].Aircraft = "Ту-154";
                if (Arr[3] == "SSJ 100") reis[N].Aircraft = "SSJ 100";
                if (Arr[3] == "Airbus A310") reis[N].Aircraft = "Airbus A310";
                if (Arr[3] == "Airbus A330") reis[N].Aircraft = "Airbus A330";
                if (Arr[3] == "Boeing-747") reis[N].Aircraft = "Boeing-747";
                reis[N].freep = Convert.ToInt32(Arr[6]);
                reis[N].Price = Convert.ToDouble(Arr[5]);
                reis[N].Tup = Arr[2];
                reis[N].Tupmin = Arr[7];
                reis[N].Tdown = Arr[4];
                reis[N].Tdownmin = Arr[8];
                N++;
            }
            SR.Close();
            return N;
        }
        
    }
   

}
