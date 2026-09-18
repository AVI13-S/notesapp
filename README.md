# Notes App — API Endpoints

Base URL: `http://localhost:8080/api/notes`

---

### 1. Create a note

```
POST /api/notes
```

**Body type:** `multipart/form-data`

| Field | Type | Required |
|---|---|---|
| `title` | Text | Yes |
| `content` | Text | Yes |
| `image` | File (PNG/JPEG) | No |

---

### 2. Get all notes

```
GET /api/notes
```

No parameters, no body.

---

### 3. Get a note by ID

```
GET /api/notes/{id}
```

| Path parameter | Type |
|---|---|
| `id` | UUID |

---

### 4. Update a note

```
PUT /api/notes/{id}
```

| Path parameter | Type |
|---|---|
| `id` | UUID |

**Body type:** `multipart/form-data`

| Field | Type | Required |
|---|---|---|
| `title` | Text | Yes |
| `content` | Text | Yes |
| `image` | File (PNG/JPEG) | No |

---

### 5. Delete a note

```
DELETE /api/notes/{id}
```

| Path parameter | Type |
|---|---|
| `id` | UUID |