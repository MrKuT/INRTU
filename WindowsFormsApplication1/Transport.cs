using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication1
{
    [Serializable]
    public class Transport
    {

        public string name { get; set; }
        public int speed { get; set; }
        public int distance { get; set; }
        public string sv { get; set; }



        public Transport(string name, int distance, int speed)
        {
            this.name = name;
            this.distance = distance;
            this.speed = speed;


        }
        public float bTime(int distance, int speed)
        {
            float time = (float)distance / speed;
            return time;
        }

    }
    [Serializable]
    public class Aircraft : Transport
    {
        public int range;
        public int load;
        public Aircraft(string name, int distance, int speed, int range, int load) : base(name, distance, speed)
        {
            this.range = range;
            this.load = load;
            this.sv = AsText();
        }
        public override string ToString()
        {
            return name;
        }
        public string AsText()
        {
            return "Дальность полета: " + this.range + " км" + Environment.NewLine +
                   "Грузоподъемность: " + this.load + " тонн";
        }
    }
    [Serializable]
    public class Train : Transport
    {
        public int wagons;
        public Train(string name, int distance, int speed, int wagons) : base(name, distance, speed)
        {
            this.wagons = wagons;
            sv = AsText();
        }
        public override string ToString()
        {
            return name;
        }
        public string AsText()
        {
            return "Кол-во вагонов: " + this.wagons;
        }
    }

}
