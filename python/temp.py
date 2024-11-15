from tkinter import *
import tkinter as tk
from tkinter import messagebox as mb
from PIL import ImageTk, Image 
import math 
from ctypes  import *

print(windll.user32.GetSystemMetrics(0))
print(windll.user32.GetSystemMetrics(1))




def goPaint(edge):     
    test = tk.Tk() 
    test.title("Площадь") 
    w=edge+315
    h=edge+30
    test.geometry('{}x{}'.format(int(w),int(h))) 
   
    c1=Canvas(test,bg="White")
    c1.pack(side="top", fill="both", expand=True)
    """Построение квадрат+секторов кругов"""
    c1.create_rectangle(10,10,10+edge,10+edge,fill="blue",outline="black",width=2)
    c1.create_arc(10+edge/2,10+edge/2,10-edge/2,10-edge/2, start=270,extent=90,fill="white",outline="black",width=2)
    c1.create_arc(10+edge/2,10+1.5*edge,10-edge/2,10+edge/2, start=0,extent=90,fill="white",outline="black",width=2)
    c1.create_arc(10+edge/2,10+1.5*edge,10+edge*1.5,10+edge/2, start=90,extent=90,fill="white",outline="black",width=2)
    c1.create_arc(10+edge/2,10+edge/2,10+1.5*edge,10-edge/2, start=180,extent=90,fill="white",outline="black",width=2)
    """Расчет закрашенной площади """
    c1.create_text(edge+150,50, text="Площадь закращенной фигуры \n S = {}".format(space(edge)),font="Verdana 12", justify="center")
    
    
def control_type(data):
        """Проверяет вводимые данные"""            
        if not data.isdigit() and data != 'null':
            mb.showerror(title="Ошибка", message="Неверный тип данных, ожидалось целое число")
        else:               
            return data
        
def space(edge):
    s = edge*edge - math.pi*((edge/2)**2)
    return s
  
def clicked():     
    edge = control_type(txt.get())    
    goPaint(float(edge))



window = tk.Tk()
window.title("Лабораторная 'Тестирование'")  
window.geometry('400x250')  
window.resizable(False, False)
lbl = Label(window, text="Введите сторону квадрата: ")  
lbl.grid(column=0, row=0)  
txt = Entry(window,width=10)  
txt.grid(column=1, row=0)  
txt.focus()
btn = Button(window, text="Клик!", command=clicked)
btn.grid(column=2, row=0)
img = ImageTk.PhotoImage(Image.open("var.png"))
image = Label(window, image = img)
image.grid(column=0, row=1,columnspan=25)  
window.mainloop()


