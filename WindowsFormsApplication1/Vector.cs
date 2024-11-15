using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WindowsFormsApplication1
{
    //==========================================  2  ====================================================
    //==========================================  Вектор  ====================================================
    class Vector
    {
        private Point fa;
        private Point fb;

        public Vector(int ax, int ay,int bx, int by)
        {
            this.fa.X = ax;
            this.fa.Y = ay;
            this.fb.X = bx;
            this.fb.Y = by;
        }

        public int x1
        {
            get
            {
                return fa.X;
            }
            set
            {
                fa.X = value;
            }
        }

        public int y1
        {
            get
            {
                return fa.Y;
            }
            set
            {
                fa.Y = value;
            }

        }
        public int x2
        {
            get
            {
                return fb.X;
            }
            set
            {
                fb.X = value;
            }
        }

        public int y2
        {
            get
            {
                return fb.Y;
            }
            set
            {
                fb.Y = value;
            }

        }
        public int coordX
        {
            get
            {
                return (fb.X - fa.X);
            }
        }
        public int coordY
        {
            get
            {
                return (fb.Y - fa.Y);
            }
        }

        public static Vector operator +(Vector v1, Vector v2)
        {
            Vector v = new Vector( v1.coordX , v1.coordY, v2.coordX,  v2.coordY );
            return v;
        }

        public static Vector operator -(Vector v1, Vector v2)
        {
            Vector v = new Vector(v1.coordX, v1.coordY, v2.coordX,  v2.coordY);
            return v;
        }

        public static double operator *(Vector v1, Vector v2)
        {
            return v1.coordX * v2.coordX + v1.coordY * v2.coordY;            
        }        
        public static double lenghtV(Vector v1)
        {
            double lenght  = Math.Sqrt(Math.Pow(v1.coordX, 2) + Math.Pow(v1.coordY, 2));          
            return  lenght;             
        }        
        public static string proizv(Vector v1, Vector v2)
        {
            double z = 0, ABx, ABy, CDx, CDy, i, j, k;
            ABx=v1.coordX;//ABx
            ABy = v1.coordY;//ABy
            CDx = v2.coordX;//CDx
            CDy = v2.coordY;//CDy

            i = ABy * z - z * CDy;
            j = ABx * z - z * CDx;
            k = ABx * CDy - ABy * CDx;

            /*AB × CD = i	 j	  k	 = i (ABy*z - z*CDy) - j (CDx*z - z*ABx) + k (ABx*CDy - ABy*CDx)
                       ABx  ABy   z 
                       CDx 	CDy   z */
            string otvet = "{" + i + " ; " + j + " ; " + k + "}";
            return otvet;
        }

    }
}
            
        //==========================================  Вектор  ====================================================
       
        //==================================================================================
        
     

