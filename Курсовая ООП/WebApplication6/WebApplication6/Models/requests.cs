//------------------------------------------------------------------------------
// <auto-generated>
//     Этот код создан по шаблону.
//
//     Изменения, вносимые в этот файл вручную, могут привести к непредвиденной работе приложения.
//     Изменения, вносимые в этот файл вручную, будут перезаписаны при повторном создании кода.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebApplication6.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    public partial class requests
    {
        public int id_requests { get; set; }        
        [Display(Name = "Пользователь")]
        public int users_id_users { get; set; }
        [Display(Name = "Администратор")]
        public Nullable<int> admin_id_Admin { get; set; }       
        [Display(Name = "Статус")]
        public byte status_req_id_status { get; set; }
        [Required(ErrorMessage = "Заполните поле")]
        [Display(Name = "Запрос")]
        public string requests1 { get; set; }
        
        [Display(Name = "Время запроса")]
        public System.DateTime time { get; set; }
        
        [Display(Name = "Комментарий")]
        public string comment_req { get; set; }
    
        public virtual admin admin { get; set; }
        public virtual status_req status_req { get; set; }
        public virtual users users { get; set; }
    }
}
