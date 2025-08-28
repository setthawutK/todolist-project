import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { TodoService } from '@shared/services/todo/todo.service';
import { NotificationService } from '@shared/services/notification';
// import { Router } from '@angular/router';

// type Todo = { id: string; text: string; done: boolean; editing?: boolean };

interface TodoItem {
  order_id: string;
  dairy_info?: string;
  boolean_check?: boolean;
  done?: boolean;
  editing?: boolean;
}

@Component({
  selector: 'app-todo',
  standalone: true,
  imports: [FormsModule, NgClass],
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TodoComponent {
  private readonly _todoService: TodoService = inject(TodoService);
  // private readonly _rounter: Router = inject(Router);
  private readonly _notify: NotificationService = inject(NotificationService);
  private readonly _cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  todos: TodoItem[] = [];

  draft: string = '';
  editDraft: Record<string, string> = {};

  constructor() {
    this.loadTodos();
  }

  loadTodos() {
    this._notify.startSpinner();
    this._todoService.getTodos().subscribe({
      next: (res: TodoItem[]) => {
        this.todos = res;
        this._cdr.markForCheck();
        this._notify.stopSpinner();
      },
      error: err => {
        this._cdr.markForCheck();
        this._notify.stopSpinner();
      },
    });
  }

  delete(id: string) {
    this._notify.startSpinner();
    this._todoService.deleteTodo(id).subscribe({
      next: res => {
        this.loadTodos();
      },
      error: err => {
        this._cdr.markForCheck();
        this._notify.stopSpinner();
      },
    });
  }

  // เพิ่มงานใหม่
  add() {
    const text = this.draft;
    if (!text) return;

    this._notify.startSpinner();
    const todoBody = { dairy_info: text };
    this._todoService.addTodo(todoBody).subscribe({
      next: (res: TodoItem) => {
        this._notify.stopSpinner();
        this.loadTodos();
      },
      error: () => {
        this._notify.stopSpinner();
      },
    });
  }

  // สลับสถานะ done/undone
  toggleCheck(todo: TodoItem) {
    const updateBody = { orderID: todo.order_id, dairy_info: todo.dairy_info, check: !todo.boolean_check };
    this._todoService.updateTodo(updateBody).subscribe({
      next: res => {
        this.loadTodos();
      },
      error: err => {
        this._cdr.markForCheck();
        this._notify.stopSpinner();
      },
    });
  }

  trackByOrderID(index: number, item: any): string {
    return item.order_id;
  }

  updateText(todo: TodoItem, newText: string) {
    const updateBody = { orderID: todo.order_id, dairy_info: newText, check: todo.boolean_check };
    this._todoService.updateTodo(updateBody).subscribe({
      next: res => {
        this.loadTodos();
      },
      error: err => {
        this._cdr.markForCheck();
        this._notify.stopSpinner();
      },
    });
  }

  // ลบงาน
  remove(id?: string) {}

  // เริ่มแก้ไข
  startEdit(id?: string) {}

  // บันทึกการแก้ไข
  saveEdit(id?: string) {}

  // ยกเลิกการแก้ไข
  cancelEdit(id?: string) {}

  // Counters
  get completed() {
    return this.todos.filter(t => t.boolean_check).length;
  }
  get uncompleted() {
    return this.todos.filter(t => !t.boolean_check).length;
  }
}
