namespace WindowsFormsApplication4
{
    partial class Form5
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.reisBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.numb = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.City = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Tup = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Tdown = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Aircraft = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Price = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Nreisa = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.freep = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.reisBindingSource1 = new System.Windows.Forms.BindingSource(this.components);
            this.reisBindingSource2 = new System.Windows.Forms.BindingSource(this.components);
            this.reisBindingSource3 = new System.Windows.Forms.BindingSource(this.components);
            this.button1 = new System.Windows.Forms.Button();
            this.reisObjectBindingSource = new System.Windows.Forms.BindingSource(this.components);
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource3)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisObjectBindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // numb
            // 
            this.numb.DataPropertyName = "numb";
            this.numb.HeaderText = "№";
            this.numb.Name = "numb";
            this.numb.ReadOnly = true;
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.numb,
            this.City,
            this.Tup,
            this.Tdown,
            this.Aircraft,
            this.Price,
            this.Nreisa,
            this.freep});
            this.dataGridView1.Location = new System.Drawing.Point(32, 28);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(844, 356);
            this.dataGridView1.TabIndex = 0;
            // 
            // City
            // 
            this.City.HeaderText = "Город";
            this.City.Name = "City";
            this.City.ReadOnly = true;
            // 
            // Tup
            // 
            this.Tup.HeaderText = "Время взлета";
            this.Tup.Name = "Tup";
            this.Tup.ReadOnly = true;
            // 
            // Tdown
            // 
            this.Tdown.HeaderText = "Время посадки";
            this.Tdown.Name = "Tdown";
            this.Tdown.ReadOnly = true;
            // 
            // Aircraft
            // 
            this.Aircraft.HeaderText = "Тип самолета";
            this.Aircraft.Name = "Aircraft";
            this.Aircraft.ReadOnly = true;
            // 
            // Price
            // 
            this.Price.HeaderText = "Стоимость билета";
            this.Price.Name = "Price";
            this.Price.ReadOnly = true;
            // 
            // Nreisa
            // 
            this.Nreisa.HeaderText = "Номер рейса";
            this.Nreisa.Name = "Nreisa";
            this.Nreisa.ReadOnly = true;
            // 
            // freep
            // 
            this.freep.HeaderText = "Свободных мест";
            this.freep.Name = "freep";
            this.freep.ReadOnly = true;
            // 
            // reisBindingSource1
            // 
            this.reisBindingSource1.DataSource = typeof(WindowsFormsApplication4.Global.Reis);
            // 
            // reisBindingSource2
            // 
            this.reisBindingSource2.DataSource = typeof(WindowsFormsApplication4.Global.Reis);
            // 
            // reisBindingSource3
            // 
            this.reisBindingSource3.DataSource = typeof(WindowsFormsApplication4.Global.Reis);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(740, 403);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(136, 37);
            this.button1.TabIndex = 1;
            this.button1.Text = "Закрыть";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // reisObjectBindingSource
            // 
            this.reisObjectBindingSource.DataSource = typeof(WindowsFormsApplication4.ReisObject);
            // 
            // Form5
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(890, 448);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dataGridView1);
            this.MaximumSize = new System.Drawing.Size(906, 487);
            this.MinimumSize = new System.Drawing.Size(906, 487);
            this.Name = "Form5";
            this.Text = "Form5";
            this.Deactivate += new System.EventHandler(this.Form5_Deactivate);
            this.Load += new System.EventHandler(this.Form5_Load);
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisBindingSource3)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.reisObjectBindingSource)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.BindingSource reisBindingSource;
        private System.Windows.Forms.DataGridViewTextBoxColumn numb;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.BindingSource reisBindingSource1;
        private System.Windows.Forms.BindingSource reisBindingSource2;
        private System.Windows.Forms.BindingSource reisBindingSource3;
        private System.Windows.Forms.BindingSource reisObjectBindingSource;
        private System.Windows.Forms.DataGridViewTextBoxColumn City;
        private System.Windows.Forms.DataGridViewTextBoxColumn Tup;
        private System.Windows.Forms.DataGridViewTextBoxColumn Tdown;
        private System.Windows.Forms.DataGridViewTextBoxColumn Aircraft;
        private System.Windows.Forms.DataGridViewTextBoxColumn Price;
        private System.Windows.Forms.DataGridViewTextBoxColumn Nreisa;
        private System.Windows.Forms.DataGridViewTextBoxColumn freep;
        private System.Windows.Forms.Button button1;
    }
}