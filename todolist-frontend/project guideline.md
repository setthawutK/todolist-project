# Project Structure Guideline

การเขียนโค้ดที่ดีช่วยให้ทีมทำงานง่ายขึ้นเพราะโค้ดที่อ่านง่าย มีโครงสร้างชัดเจน และมีการตั้งชื่อที่สื่อความหมาย จะทำให้สมาชิกทีมเข้าใจได้รวดเร็ว ลดเวลาในการทำความเข้าใจ และลดความผิดพลาดเมื่อต้องแก้ไขหรือพัฒนาต่อ

## Folders

```ts
├── ci // Custom tasks
├── node_modules // โฟลเดอร์ที่ใช้เก็บแพ็กเกจที่ติดตั้งผ่าน npm
├── src
|   ├── app
|   |   ├── modules // โมดูลหลักของแอปพลิเคชัน
|   |   |   └── qutation // component home
|   |   |       ├── interface // โฟลเดอร์แชร์สำหรับ modules
|   |   |       ├── service // โฟลเดอร์แชร์สำหรับ modules
|   |   |       ├── shared // โฟลเดอร์แชร์สำหรับ modules
|   |   |       |   ├── component // โฟลเดอร์ component แชร์สำหรับ modules
|   |   |       |   └── price-calculator.ts
|   |   |       └──  pages // โฟลเดอร์สำหรับรวม page ต่างๆ
|   |   |           ├── home
|   |   |           |   ├── home.component.html
|   |   |           |   ├── home.component.scss
|   |   |           |   └── home.component.ts
|   |   |           └── view-detail
|   |   |               ├── view-detail.component.html
|   |   |               ├── view-detail.component.scss
|   |   |               └── view-detail.component.ts
|   |   |
|   |   ├── shared // โฟลเดอร์แชร์สำหรับโปรเจค
|   |   |   ├── api // โฟลเดอร์สำหรับเก็บ path api
|   |   |   |   └── index.d.ts // ไฟล์สำหรับเก็บ path api
|   |   |   |
|   |   |   ├── component // โฟลเดอสำหรับเก็บ reuse component
|   |   |   |   └── dialogs // ไฟล์สำหรับเก็บ reuse dialog component
|   |   |   |
|   |   |   ├── constants // เก็บตัวแปรที่คงที่,
|   |   |   |   ├── address.ts // ไฟล์ constants address
|   |   |   |   └── index.ts // ไฟล์รวม constants
|   |   |   |
|   |   |   ├── directives // โฟลเดอร์สำหรับเก็บ directive
|   |   |   |   └── index.d.ts // ไฟล์สำหรับเก็บ path api
|   |   |   |
|   |   |   ├── error
|   |   |   |   ├── error-code-mapping.ts // ไฟล์สำหรับ mapping error code
|   |   |   |   └── error-handler-utils.ts // ไฟล์เขียน logic จัดการ error
|   |   |   |
|   |   |   ├── guards // โฟลเดอร์สำหรับเก็บ Route Guards เพื่อควบคุมการเข้าถึงหน้าในแอปพลิเคชัน
|   |   |   |
|   |   |   ├── network // โฟลเดอร์สำหรับเก็บ HTTP interceptors
|   |   |   |
|   |   |   ├── pipes // โฟลเดอร์สำหรับเก็บ pipes ที่ใช้ในการแปลงข้อมูล
|   |   |   |
|   |   |   ├── services // โฟลเดอร์สำหรับเก็บ services ที่ใช้ในโปรเจค
|   |   |   |   └── home // โฟลเดอร์สำหรับเก็บ home services
|   |   |   |   |   └── home.service.ts
|   |   |   |
|   |   |   ├── util // โฟลเดอร์สำหรับเก็บ util
|   |   |   |
|   |   |   ├── swagger // โฟลเดอร์สำหรับเก็บ api generate จาก swagger
|   |   |   |
|   |   |   ├── typings // โฟลเดอร์สำหรับเก็บตัวแปร declare enum
|   |   |   |
|   |   |   ├── validators // โฟลเดอร์สำหรับเก็บ validators ที่ใช้ในโปรเจค
|   |   |   |
|   |   ├── styles // โฟลเดอร์สำหรับเก็บ custom style
|   |   |   └── common/dropdown.scss // ไฟล์สำหรับ custom style
|   |   |
|   |   ├── app.component.html
|   |   ├── app.component.scss
|   |   ├── app.component.ts // ไฟล์ที่ใช้กำหนดคอมโพเนนต์หลักของแอปพลิเคชัน
|   |   ├── app.config.ts // ตั้งต่า application providers config
|   |   └── app.routes.ts // จัดการ routes
|   |
|   ├── assets // โฟลเดอร์สำหรับเก็บไฟล์รูปภาพ, icon, ไฟล์ JSON หรือไฟล์สื่ออื่น ๆ
|   |   ├── generate // โฟลเดอร์สำหรับเก็บไฟล์ icon
|   |   |
|   |   ├── i18n // โฟลเดอร์สำหรับเก็บไฟล์ JSON แต่ละภาษา
|   |   |
|   |   └── images // โฟลเดอร์สำหรับเก็บไฟล์รูปภาพ
|   |       ├── svg // โฟลเดอร์สำหรับเก็บไฟล์รูปภาพ svg
|   |       └── png // โฟลเดอร์สำหรับเก็บไฟล์รูปภาพ png
|   |
|   ├── environments // โฟลเดอร์สำหรับเก็บไฟล์คอนฟิกของแต่ละ environment (เช่น development, production)
|   |
|   └── proxy // ไฟล์กำหนดค่าพร็อกซีสำหรับ API request ระหว่าง development
|
├── .editorconfig // ไฟล์กำหนดค่าการจัดรูปแบบโค้ด
├── .gitignore // ไฟล์ที่ใช้ระบุไฟล์หรือโฟลเดอร์ที่ไม่ต้องการให้ติดตามใน Git
├── .prettierrc.json // ไฟล์กำหนดค่าการจัดรูปแบบโค้ดด้วย Prettier
├── angular.json // ไฟล์คอนฟิกของ Angular เช่น การตั้งค่า build และ assets
├── dockerfile  // ไฟล์ที่ใช้สำหรับสร้าง Docker image ของโปรเจกต์
├── jenkinsfile  // ไฟล์กำหนด pipeline สำหรับ CI/CD บน Jenkins
├── nginx.conf // ไฟล์คอนฟิกของ Nginx ที่ใช้เป็นเว็บเซิร์ฟเวอร์หรือ reverse proxy
├── tailwind.config.js // ไฟล์คอนฟิกสำหรับ Tailwind CSS
├── tsconfig.json // ไฟล์กำหนดค่าการคอมไพล์ TypeScript
├── package.json // ไฟล์ที่ใช้เก็บข้อมูลแพ็กเกจที่โปรเจกต์ใช้ รวมถึงสคริปต์ที่ใช้รันคำสั่งต่าง ๆ
└── README.md // ไฟล์เอกสารที่อธิบายรายละเอียดของโปรเจกต์
```

## การตั้งชื่อ Class, Interface, Type, Enum

#### ขึ้นต้นตัวย PascalCase คือ แต่ละคำขึ้นต้นด้วยตัวใหญ่ ไม่มีช่องว่างหรือเครื่องหมายคั่น

```ts
class User {}
interface User {}
type User {}
enum User {}
```

## การประกาศตัวแปร

#### ขึ้นต้นด้วย camelCase คือ คำแรกขึ้นต้นด้วยตัวเล็ก คำถัดไปขึ้นต้นด้วยตัวใหญ่ ไม่มีช่องว่าง

#### ตัวแปรที่เป็น private จะใส่ `_` หน้าชื่อ

```ts
private readonly _router: Router = inject(Router);
```

#### ตัวแปรที่ใช้ค่าจาก ENUM จะเป็น `UPPER CASE` ทั้งหมด

```ts
readonly MENU_ENUM = GetMyMenuSpecificationResDto.MenuEnum;
```

#### ตัวแปรที่เป็น observable จะใส่ `$` หลังชื่อตัวแปร

```ts
private refreshTokenSubject$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
```

#### ตัวแปรทั่วไปจะระบุ type และ comment แยกกลุ่มตัวแปร

⚠️ **เลี่ยงการใช้ `any` โดยไม่จำเป็น**

```ts
// user
selectedUserId: number;

// menu
menus: GetMyMenuResDto[] = [];
selectedMenu: number;

// form
addressForm: FormGroup<AddressFormModel>;
```

## ลำดับการเรียงตัวแปร

#### การเรียงตัวแปรที่ทำให้อ่านง่าย.

1. **@Declarator**
2. **Injection public -> private**
3. **Declare obs**
4. **Constants**
5. **Component variable**

#### ⚠️ การเรียงลำดับที่ทำให้อ่านตัวแปรลำบาก

```ts
private readonly _notify: NotificationService = inject(NotificationService);
@Output() viewCard: EventEmitter<boolean> = new EventEmitter();

@HostListener('window:orientationchange', ['$event'])
onOrientationChange() {}
@Output() viewDetail: EventEmitter<boolean> = new EventEmitter();
isHide: boolean;
private refreshTokenSubject$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

@Input() data!: GetMyRelatePolicyResDto | GetMyPolicyResDto;
private readonly _dialogService: DialogService = inject(DialogService);
private readonly _cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

id: number;
readonly ITEM_TYPE_ENUM = ItemResDto.TypeEnum;

readonly CATEGORY_TYPE_ENUM = GetAllItemCooperativeResDto.CategoryTypeEnum;
@ViewChild('card') card!: ElementRef;
cardId: number;
```

#### ✅ การเรียงลำดับที่ทำให้อ่านตัวแปรง่าย

```ts
@Input() data!: GetMyRelatePolicyResDto | GetMyPolicyResDto;

@Output() viewCard: EventEmitter<boolean> = new EventEmitter();
@Output() viewDetail: EventEmitter<boolean> = new EventEmitter();

@HostListener('window:orientationchange', ['$event'])
onOrientationChange() {}

@ViewChild('card') card!: ElementRef;

private readonly _dialogService: DialogService = inject(DialogService);
private readonly _notify: NotificationService = inject(NotificationService);
private readonly _cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

readonly CATEGORY_TYPE_ENUM = GetAllItemCooperativeResDto.CategoryTypeEnum;
readonly ITEM_TYPE_ENUM = ItemResDto.TypeEnum;

private refreshTokenSubject$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

id: number;

//card
isHide: boolean;
cardId: number;
```

## ลำดับความสำคัญของ Function

#### ⚠️ การเรียง function ที่ทำให้อ่านโค๊ดยาก

```diff
- getCount() {};
- constructor() {};
- getLabelName() {};
- onSubmit() {};
- ngOnInit() {};
```

#### ✅ เรียง function ที่จำเป็นต้องมี -> ที่สำคัญ -> ไม่สำคัญ ตามลำดับ และ เว้นบรรทัดหลังจากจบ function

```diff
+ constructor() {};
+
+ ngOnInit() {};
+
+ onSubmit() {};
+
+ getCount() {};
+
+ getLabelName() {};
```

- **`function ที่จำเป็นต้องมี`** คือ function บังคับมีหลังจาก implements เช่น constructor(), ngOnInit(), ngAfterViewInit() เป็นต้น

- **`function ที่สำคัญ`** คือ function ที่มี login หรือเรียก api เช่น onSubmit(), onCreate() เป็นต้น

- **`function ไม่สำคัญ`** คือ function ที่เขียนแล้วไม่จำเป็นต้องกลับไปแก้บ่อยๆ เช่น function private, getCount(), getName() เป็นต้น

## การใช้ HTML5 tags ให้ตรงวัตถุประสงค์

อ่านเพิ่มเติม https://www.goragod.com/knowledge/html5_tags.html

ตัวอย่างเช่น การใช้ **`footer, nav`** แทนการเขียนด้วย **`div`**

## การใช้ indexerator รวมไฟล์ ts ใน folder เป็น index.ts

#### ประโยชน์

- ลดการเขียน import ซ้ำซ้อน
- ทำให้โค้ดสะอาดและจัดการง่ายขึ้น

#### วิธีการใช้งาน

- ลง extension ใน vscode `https://marketplace.visualstudio.com/items?itemName=hemo.indexerator`
- คลิ๊กขวาที่ folder ที่ข้างในเป็นไฟล์ ts แล้วเลือก `indexerator` หลังจากนั้นจะได้ไฟล์ `index.ts`

## 🎯 หลักการคิดในการ Reuse Component

1. **แยกส่วนประกอบที่ใช้ซ้ำได้**

   - วิเคราะห์องค์ประกอบใน UI ที่สามารถใช้ซ้ำได้ เช่น ปุ่ม, การ์ด, ฟอร์ม, navbar, footer
   - หากหน้า HTML มี Component จำนวนมาก ควรแยกออกมาเป็น Component ย่อยเพื่อให้โค้ดอ่านง่ายและจัดการได้สะดวกขึ้น
   - หลีกเลี่ยงการคัดลอกโค้ดเดิมแล้วนำไปวางใหม่ (Copy-Paste Code) ซึ่งอาจทำให้โค้ดซ้ำซ้อนและดูแลยาก

2. **กำหนดขอบเขตของ Component**

   - แต่ละ Component ควรมีหน้าที่ที่ชัดเจนและไม่ควรทำหลายอย่างพร้อมกัน
   - ควรออกแบบให้ Component รับค่าผ่าน `@Input()` และส่งค่ากลับผ่าน `@Output()` เพื่อให้สามารถใช้ซ้ำได้ง่าย

3. **แยก Logic ออกจาก UI**

   - หาก Component มีการดึงข้อมูลหรือมี Business Logic มาก ควรแยกไปไว้ใน `ts` เพื่อลดความซับซ้อนของ Componen

4. **ทำให้ Component ยืดหยุ่น**
   - ใช้ `@Input()` เพื่อกำหนดค่าที่สามารถปรับแต่งได้ เช่น ชื่อ, สี, หรือสไตล์ของ Component
   - ใช้ `@Output()` เพื่อให้ Component สามารถแจ้งเหตุการณ์กลับไปยัง Parent Component ได้

## วิธีการ custome input style

1. ไปที่ src/app/styles/common/`ชื่อประเภท input` และ สร้างไฟล์ชื่อ input นั้นๆ
2. ใส่ `@include uri` ใน `src/styles.scss`

## ตรวจเช็คก่อน commit code

1. รันโค้ดให้แน่ใจว่าทำงานถูกต้อง และ ตรงตามความต้องการของงาน
2. ดู code diff เพื่อตรวจสอบการเปลี่ยนแปลง, ตรวจว่าไม่มีข้อมูลส่วนตัวหรือคีย์ความลับในโค้ด และ ลบโค๊ดในส่วนที่ไม่ใช้ออก
3. เขียน commit message ที่ชัดเจน

## ปัญหาที่พบบ่อย

1. การกำหนดขนาดแบบตายตัวมากเกินไป (Fixed Width, Height, Margin, Padding)
2. การไม่ทดสอบ Responsive ทุกขนาด
3. การไม่แยก Utility Function ให้สามารถใช้ซ้ำได้ \*
4. การไม่ตรวจสอบ API ว่ายิงซ้ำซ้อนหรือไม่
5. HTML, ts จำนวนโค๊ดไม่ควรเยอะเกินไป `ข้อแนะนำ 300-500 บรรทัด ต่อไฟล์ เป็นขนาดที่เหมาะสม`
6. function จำนวนโค๊ดไม่ควรเยอะเกินไป `ข้อแนะนำไม่ควรเกิน 100 บรรทัด ต่อ function`
7. การเขียนโค๊ดเกินความจำเป็น เช่น div ซ้อนกันหลายๆชั้น
8. ไม่ clean code ก่อน commit
9. หลังจากเสร็จแล้วไม่ commit code ตาม modules
10. tailwind class ใน html ที่ใช้ซ้ำกันเกิน 3 ครั้ง ให้สร้างเป็น class ใน scss แล้วเรียกมาใช้แทน
11. code ที่ comment ให้ใส่เหตุผลไว้เสมอ และ ถ้าไม่ใช้แล้วให้ลบทิ้ง
12. code ที่ copy, gen ai หรือ ไม่มั่นใจ ให้ใส้ // TODO: xxxxxx
