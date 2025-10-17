# Waste Management System API Documentation

A comprehensive waste management system that enables users to manage waste collection, purchase coin packages, provide feedback, and handle complaints and disputes.

## Table of Contents

- [Overview](#overview)
- [Base URL](#base-url)
- [User Roles](#user-roles)
- [API Endpoints](#api-endpoints)
    - [Coin Package Management](#coin-package-management)
    - [Waste Management](#waste-management)
    - [Feedback System](#feedback-system)
    - [Complaint Management](#complaint-management)
    - [Payment Management](#payment-management)
    - [Refund System](#refund-system)
    - [Dispute Management](#dispute-management)
- [Response Format](#response-format)
- [Status Codes](#status-codes)

## Overview

This system provides a complete solution for waste management operations, including:
- Coin-based payment system for waste collection
- Real-time sensor reading for garbage bins
- User feedback and complaint management
- Payment processing and dispute resolution
- Administrative oversight and refund processing

## Base URL
```
http://localhost:8080
```

## User Roles

- **User**: Can purchase packages, view sensor readings, submit feedback, create complaints, and manage disputes
- **Waste Management Employee**: Can read sensors and collect waste
- **Admin**: Can manage complaints, view all payments, process refunds, and resolve disputes

---

## API Endpoints

### Coin Package Management

#### Get All Coin Packages
Get a list of all available coin packages for purchase.

- **URL**: `/coinpackage/getall`
- **Method**: `GET`
- **Access**: User

**Response:**
```json
{
  "statusCode": "02",
  "message": "Available packages",
  "content": [
    {
      "name": "Silver",
      "price": 2000.0,
      "coins": 1000,
      "id": 1
    },
    {
      "name": "Gold",
      "price": 5000.0,
      "coins": 2000,
      "id": 2
    }
  ]
}
```

#### Buy Package
Purchase a coin package to add coins to user account.

- **URL**: `/coinpackage/buy-package`
- **Method**: `POST`
- **Access**: User

**Request Body:**
```json
{
  "packageId": "1",
  "userId": "1"
}
```

**Response:**
```json
{
  "statusCode": "02",
  "message": "Package bought successfully",
  "content": {
    "paymentId": 9,
    "userId": 1,
    "amount": 2000.0,
    "paymentDate": "2025-10-16T19:17:59.7636191",
    "paymentMethod": "CARD"
  }
}
```

---

### Waste Management

#### Sensor Reading
Scan a specific bin and retrieve its sensor data.

- **URL**: `/waste/sensor-reading/{binId}`
- **Method**: `GET`
- **Access**: Waste Management Employee

**Response:**
```json
{
  "statusCode": "02",
  "message": "Garbage Bin Sensor Reading",
  "content": {
    "readingId": "2",
    "binId": "2",
    "timestamp": "2025-10-15T23:41:29",
    "weight": 20.0,
    "binStatus": "F"
  }
}
```

#### Collect Waste
Confirm waste collection and deduct coins from user account.

- **URL**: `/waste/collect`
- **Method**: `POST`
- **Access**: Waste Management Employee

**Request Body:**
```json
{
  "binId": "2",
  "wasteType": "PLASTIC"
}
```

**Response:**
```json
{
  "statusCode": "00",
  "message": "Collection successful",
  "content": {
    "collectionId": "5fcd6cdf-a08d-4d2f-a85d-ba6740a773b7",
    "binId": "2",
    "collectionDate": "2025-10-16",
    "wasteType": "PLASTIC",
    "quantity": 20.0,
    "ownerId": 2
  }
}
```

---

### Feedback System

#### Publish Feedback
Submit feedback about waste collection service.

- **URL**: `/feedback/publish`
- **Method**: `POST`
- **Access**: User

**Request Body:**
```json
{
  "feedbackDate": "2025-10-16",
  "rating": 5,
  "comment": "Great service, would recommend!"
}
```

**Response:**
```json
{
  "statusCode": "02",
  "message": "feedback successfully published",
  "content": {
    "feedbackId": 2,
    "feedbackDate": "2025-10-16",
    "rating": 5,
    "comment": "Great service, would recommend!",
    "isAnonymous": true,
    "createdAt": "2025-10-16T19:26:04.0667755"
  }
}
```

---

### Complaint Management

#### Create Complaint
File a complaint about the overall service.

- **URL**: `/complaints/create`
- **Method**: `POST`
- **Access**: User

**Request Body:**
```json
{
  "userId": 1,
  "category": "Service Delay",
  "description": "The garbage collection truck did not arrive on the scheduled date.",
  "evidenceUrl": "https://example.com/uploads/truck-missed.jpg",
  "preferredContact": "0123456789"
}
```

**Response:**
```json
{
  "statusCode": "00",
  "message": "Complaint created successfully",
  "content": {
    "complaintId": "2685cded-ba92-419a-a35e-b4c822fd97d7",
    "userId": 1,
    "category": "Service Delay",
    "description": "The garbage collection truck did not arrive on the scheduled date.",
    "status": "OPEN",
    "evidenceUrl": "https://example.com/uploads/truck-missed.jpg",
    "createdAt": "2025-10-16 19:07:36",
    "preferredContact": "0123456789"
  }
}
```

#### Get User Complaints
Retrieve all complaints filed by a specific user.

- **URL**: `/complaints/user/{userId}`
- **Method**: `GET`
- **Access**: User

**Response:**
```json
{
  "statusCode": "00",
  "message": "User complaints retrieved successfully",
  "content": [
    {
      "complaintId": "24d782c8-3f64-4fb5-91ad-3b0cc96bbac4",
      "userId": 1,
      "category": "Technical Issue",
      "description": "The website is down, unable to submit forms.",
      "status": "CLOSE",
      "evidenceUrl": "https://example.com/evidence-screenshot.png",
      "createdAt": "2025-10-16 19:07:36",
      "preferredContact": "Email"
    }
  ]
}
```

#### Get All Complaints
Retrieve all complaints in the system (Admin only).

- **URL**: `/complaints/admin/all`
- **Method**: `GET`
- **Access**: Admin

**Response:**
```json
{
  "statusCode": "00",
  "message": "All complaints retrieved successfully",
  "content": [
    {
      "complaintId": "24d782c8-3f64-4fb5-91ad-3b0cc96bbac4",
      "userId": 1,
      "category": "Technical Issue",
      "description": "The website is down, unable to submit forms.",
      "status": "CLOSE",
      "evidenceUrl": "https://example.com/evidence-screenshot.png",
      "createdAt": "2025-10-16 19:07:36",
      "preferredContact": "Email"
    }
  ]
}
```

#### Close Complaint
Close a complaint after processing (Admin only).

- **URL**: `/complaints/{complaintId}/close`
- **Method**: `PUT`
- **Access**: Admin

**Response:**
```json
{
  "statusCode": "00",
  "message": "Complaint closed successfully",
  "content": {
    "complaintId": "24d782c8-3f64-4fb5-91ad-3b0cc96bbac4",
    "userId": 1,
    "category": "Technical Issue",
    "description": "The website is down, unable to submit forms.",
    "status": "CLOSE",
    "evidenceUrl": "https://example.com/evidence-screenshot.png",
    "createdAt": "2025-10-16 19:07:36",
    "preferredContact": "Email"
  }
}
```

---

### Payment Management

#### Get All Payments
Retrieve all payments in the system (Admin only).

- **URL**: `/payments/all`
- **Method**: `GET`
- **Access**: Admin

**Response:**
```json
{
  "statusCode": "02",
  "message": "All Payments",
  "content": [
    {
      "paymentId": 1,
      "userId": 1,
      "amount": 45000.0,
      "paymentDate": "2025-10-15T16:15:58",
      "paymentMethod": "card"
    }
  ]
}
```

#### Get User Payments
Retrieve all payments made by a specific user.

- **URL**: `/payments/user/{userId}`
- **Method**: `GET`
- **Access**: User

**Response:**
```json
{
  "statusCode": "02",
  "message": "User Payments",
  "content": [
    {
      "paymentId": 1,
      "userId": 1,
      "amount": 45000.0,
      "paymentDate": "2025-10-15T16:15:58",
      "paymentMethod": "card"
    }
  ]
}
```

---

### Refund System

#### Process Refund
Process a refund after resolving a user dispute (Admin only).

- **URL**: `/refund/process`
- **Method**: `POST`
- **Access**: Admin

**Request Body:**
```json
{
  "paymentId": "9",
  "reason": "customer didnt received coins after payment"
}
```

**Response:**
```json
{
  "statusCode": "00",
  "message": "Refund processed successfully",
  "content": {
    "refundId": 3,
    "paymentId": 9,
    "amount": 2000.0,
    "approvedAt": "2025-10-16T19:42:58.8715763",
    "reason": "customer didnt received coins after payment",
    "status": "APPROVED"
  }
}
```

---

### Dispute Management

#### Create Dispute
Create a dispute regarding a payment issue.

- **URL**: `/dispute/create`
- **Method**: `POST`
- **Access**: User

**Request Body:**
```json
{
  "paymentId": "8",
  "userId": "1",
  "reason": "charged 2 times"
}
```

**Response:**
```json
{
  "statusCode": "00",
  "message": "Dispute created successfully",
  "content": {
    "disputeId": "634614a5-8c11-4e75-8b78-ed8a2d19c8a6",
    "userId": 1,
    "paymentId": 8,
    "reason": "charged 2 times",
    "openAt": "2025-10-16T19:45:14.6751038",
    "status": "PENDING"
  }
}
```

#### Resolve Dispute
Mark a dispute as resolved (Admin only).

- **URL**: `/dispute/resolve/{disputeId}`
- **Method**: `PUT`
- **Access**: Admin

**Response:**
```json
{
  "statusCode": "00",
  "message": "Dispute resolved successfully",
  "content": {
    "disputeId": "18605273-7c41-4e62-a8d0-0830a66b3e78",
    "userId": 1,
    "paymentId": 1,
    "reason": "didnt received coins",
    "openAt": "2025-10-16T19:07:36",
    "status": "RESOLVED"
  }
}
```

#### Get All Disputes
Retrieve all disputes in the system (Admin only).

- **URL**: `/dispute/all`
- **Method**: `GET`
- **Access**: Admin

**Response:**
```json
{
  "statusCode": "00",
  "message": "Disputes found",
  "content": [
    {
      "disputeId": "18605273-7c41-4e62-a8d0-0830a66b3e78",
      "userId": 1,
      "paymentId": 1,
      "reason": "didnt received coins",
      "openAt": "2025-10-16T19:07:36",
      "status": "RESOLVED"
    }
  ]
}
```

---

## Response Format

All API responses follow a consistent format:
```json
{
  "statusCode": "string",
  "message": "string",
  "content": {}
}
```

- **statusCode**: Indicates the result of the operation
- **message**: Human-readable description of the result
- **content**: The actual data payload (can be an object or array)

## Status Codes

- `00`: Success (Operation completed successfully)
- `02`: Success with data retrieved
- Other codes may indicate specific error conditions

## Error Handling

When an error occurs, the API will return an appropriate HTTP status code along with an error message in the response body. Always check the `statusCode` field in the response to determine if the operation was successful.

