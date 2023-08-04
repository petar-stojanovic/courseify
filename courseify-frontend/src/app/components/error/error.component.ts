import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {
  errorCode = 404;
  defaultErrorMessage = 'Unknown error';


  errorMessages: { [code: number]: { title: string, description: string } } = {
    400: {
      title: 'Bad Request',
      description: 'The server could not understand the request you made.'
    },
    401: {
      title: 'Unauthorized',
      description: 'You are not allowed to access this resource. Please log in or request access from the administrator.'
    },
    403: {
      title: 'Forbidden',
      description: 'You do not have permission to access this resource.'
    },
    404: {
      title: 'Not Found',
      description: 'The link you followed is either broken or the page has been removed.'
    },
    405: {
      title: 'Method Not Allowed',
      description: 'The request method is not supported for the requested resource.'
    },
    408: {
      title: 'Request Timeout',
      description: 'The server timed out while waiting for the request to be completed.'
    },
    429: {
      title: 'Too Many Requests',
      description: 'You have sent too many requests in a short amount of time. Please try again later.'
    },
    500: {
      title: 'Internal Server Error',
      description: 'The server encountered an unexpected error and was unable to complete your request. Please try again later.'
    },
    502: {
      title: 'Bad Gateway',
      description: 'The server received an invalid response from another server. Please try again later.'
    },
    503: {
      title: 'Service Unavailable',
      description: 'The server is currently unable to handle the request. It may be down or temporarily overloaded.'
    },
    504: {
      title: 'Gateway Timeout',
      description: 'The server did not receive a timely response from another server. Please try again later. '
    },
  };

  constructor(private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.errorCode = +params['code']; // Get the error code from route parameters
    });

    if (isNaN(this.errorCode)) {
      this.errorCode = 404;
    }
  }

  getErrorMessage(property: 'title' | 'description'): string {
    if (this.errorCode in this.errorMessages) {
      return this.errorMessages[this.errorCode][property];
    }
    return this.defaultErrorMessage;
  }
}
